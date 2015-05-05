var autoprefixer = require('gulp-autoprefixer');
var gulp = require('gulp');
var minifyCSS = require('gulp-minify-css');
var minifyHTML = require('gulp-minify-html');
var sass = require('gulp-sass');

gulp.task("html", function () {
  gulp.src('src/html/index.html')
    .pipe(minifyHTML())
    .pipe(gulp.dest("resources/public"));
});

gulp.task("sass", function () {
  gulp.src('src/sass/site.scss')
    .pipe(sass())
    .pipe(autoprefixer({
      browsers: ['last 2 versions'],
      cascade: false
    }))
    .pipe(minifyCSS())
    .pipe(gulp.dest('resources/public/css'));
});

gulp.task("watch", function () {
  gulp.start("sass", "html");
  gulp.watch('sass/style.scss', ["sass"]);
  gulp.watch('html/index.html', ["html"]);
});

gulp.task("build", ["sass", "html"]);
gulp.task("default", ["watch"]);
