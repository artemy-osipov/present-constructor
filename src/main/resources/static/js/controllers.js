'use strict';

/* Controllers */

var presentControllers = angular.module('presentControllers', []);

presentControllers.controller('IndexCtrl', function() {
});

presentControllers.controller('CandyListCtrl', function($scope, $location, $anchorScroll, Candy) {
  $scope.candies = Candy.query();

  $scope.$on('$viewContentLoaded', function(){
    $("div#sticky").sticky({ topSpacing: 0 });
  });

  $scope.scrollTo = function(id) {
    var old = $location.hash();
    $location.hash(id);
    $anchorScroll();
    $location.hash(old);
  }
});

presentControllers.controller('CandyAddCtrl', function($scope) {
});

presentControllers.controller('CandyEditCtrl', function($scope, $routeParams, Candy) {
  $scope.candy = Candy.get({candyId: $routeParams.candyId});
});

presentControllers.controller('PresentAddCtrl', function($scope, $location, $anchorScroll, Candy, Present) {
  $scope.candies = Candy.query();

  $scope.$on('$viewContentLoaded', function(){
    $("div#sticky").sticky({ topSpacing: 0 });
  });

  $scope.scrollTo = function(id) {
    var old = $location.hash();
    $location.hash(id);
    $anchorScroll();
    $location.hash(old);
  }
});

presentControllers.controller('PresentListCtrl', function($scope, $location, $anchorScroll, Present) {
  $scope.presents = Present.query();

  $scope.$on('$viewContentLoaded', function(){
    $("div#sticky").sticky({ topSpacing: 0 });
  });

  $scope.scrollTo = function(id) {
    var old = $location.hash();
    $location.hash(id);
    $anchorScroll();
    $location.hash(old);
  }
});

presentControllers.controller('PresentShowCtrl', function($scope, $routeParams, Present) {
  $scope.present = Present.get({presentId: $routeParams.presentId});
});

