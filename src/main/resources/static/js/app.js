'use strict';

/* App Module */

var phonecatApp = angular.module('presentApp', [
  'ngRoute',
  'presentControllers'
]);

phonecatApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/index', {
        templateUrl: 'partials/index.html',
        controller: 'IndexCtrl'
      }).
      when('/phones/:phoneId', {
        templateUrl: 'partials/phone-detail.html',
        controller: 'PhoneDetailCtrl'
      }).
      otherwise({
        redirectTo: '/index'
      });
  }]);
