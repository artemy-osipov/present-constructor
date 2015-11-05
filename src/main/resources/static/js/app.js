'use strict';

/* App Module */

var presentApp = angular.module('presentApp', [
  'ngRoute',
  'presentControllers',
  'presentServices'
]);

presentApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/index', {
        templateUrl: 'partials/index.html',
        controller: 'IndexCtrl'
      }).
      when('/candy-list', {
        templateUrl: 'partials/candy-list.html',
        controller: 'CandyListCtrl'
      }).
      when('/candy-add', {
        templateUrl: 'partials/candy-add.html',
        controller: 'CandyAddCtrl'
      }).
      when('/candy/:candyId/edit', {
        templateUrl: 'partials/candy-edit.html',
        controller: 'CandyEditCtrl'
      }).
      when('/present-add', {
        templateUrl: 'partials/present-add.html',
        controller: 'PresentAddCtrl'
      }).
      when('/present/:presentId/add', {
        templateUrl: 'partials/present-add.html',
        controller: 'PresentAddCtrl'
      }).
      when('/present-list', {
        templateUrl: 'partials/present-list.html',
        controller: 'PresentListCtrl'
      }).
      when('/present/:presentId', {
        templateUrl: 'partials/present-show.html',
        controller: 'PresentShowCtrl'
      }).
      otherwise({
        redirectTo: '/index'
      });
  }]);
