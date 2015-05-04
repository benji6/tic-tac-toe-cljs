(ns tic-tac-toe-cljs.prod
  (:require [tic-tac-toe-cljs.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
