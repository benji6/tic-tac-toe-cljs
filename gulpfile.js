var autoprefixer = require('gulp-autoprefixer');
var gulp = require('gulp');
var minifycss = require('gulp-minify-css');
var sass = require('gulp-sass');

gulp.task('sass', function () {
  gulp.src('src/sass/site.scss')
    .pipe(sass())
    .pipe(autoprefixer({
      browsers: ['last 2 versions'],
      cascade: false
    }))
    .pipe(minifycss())
    .pipe(gulp.dest('resources/public/css'));
});

gulp.task("watch", function () {
  gulp.start('sass');
  gulp.watch('sass/style.scss', ["sass"]);
});

gulp.task("build", ["sass"]);
gulp.task("default", ["watch"]);
