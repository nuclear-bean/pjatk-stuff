var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
const cameraRouter = require('./routes/cameraRoute');
const vehicleRouter = require('./routes/vehicleRoute');
const encounterRouter = require('./routes/encounterRoute');

// API
const cameraApiRouter = require('./routes/api/CameraApiRoute');
const vehicleApiRouter = require('./routes/api/VehicleApiRoute');
const encounterApiRouter = require('./routes/api/EncounterApiRoute');


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/camera', cameraRouter);
app.use('/vehicle', vehicleRouter);
app.use('/encounter', encounterRouter);

// API
app.use('/api/camera', cameraApiRouter);
app.use('/api/camera/:camId', cameraApiRouter);

app.use('/api/vehicle', vehicleApiRouter);
app.use('/api/vehicle/:registration', vehicleApiRouter);

app.use('/api/encounter', encounterApiRouter);
app.use('/api/encounter/:encId', encounterApiRouter);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});



module.exports = app;
