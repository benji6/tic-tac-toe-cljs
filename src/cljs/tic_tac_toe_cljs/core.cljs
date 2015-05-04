(ns tic-tac-toe-cljs.core
  (:require [reagent.core :as reagent :refer [atom]]))

(def side-length 3)
(def empty-code "")
(def nought-code "O")
(def cross-code "X")
(def board-data
  (atom (vec (repeat side-length (vec (repeat side-length empty-code))))))
(def message-data (atom ""))

(defn current-player-code []
  (let [filter-count (comp count filter)]
  (if (> (filter-count #(= nought-code %) (flatten @board-data))
    (filter-count #(= cross-code %) (flatten @board-data)))
    cross-code
    nought-code)))

(defn three-in-a-row? [line]
  (or (every? #(= nought-code %) line)
    (every? #(= cross-code %) line)))

(defn all-possible-lines []
  (concat @board-data
    (map-indexed (fn [row-index row] (map #(% row-index) @board-data)) @board-data)
    [(map-indexed #((@board-data %1) %1) @board-data)
      (map-indexed #((@board-data (- side-length 1 %1)) %1) @board-data)]))

(defn victory? []
  (some three-in-a-row? (all-possible-lines)))

(defn board-full? []
  (not (some #(= empty-code %) (flatten @board-data))))

(defn valid-move? [row-index column-index]
  (let [cell-is-empty (= empty-code ((@board-data row-index) column-index))]
    (and (not (victory?)) cell-is-empty)))

(defn make-move [row-index column-index]
  (swap! board-data
    #(assoc % row-index (assoc (% row-index) column-index (current-player-code))))
  (if (victory?)
    (if (= nought-code (current-player-code))
      (reset! message-data "Victory for crosses!")
      (reset! message-data "Victory for noughts!"))
  (if (board-full?)
    (reset! message-data "Draw!"))))

(defn on-click [row-index column-index]
  (if (valid-move? row-index column-index)
    (make-move row-index column-index)))

(defn view []
  [:div.center
    [:table (doall (map-indexed (fn [row-index row]
      [:tr {:key (str "tr" row-index)} (doall (map-indexed (fn [column-index cell]
        [:td {:key (str "td" (+ (* row-index side-length) column-index))
          :on-click #(on-click row-index column-index)} cell]) row))]) @board-data))]
    [:p @message-data]])

(defn init! []
  (reagent/render [view] (.getElementById js/document "app")))
