'use strict';

/* Controllers */

var presentControllers = angular.module('presentControllers', []);

presentControllers.controller('IndexCtrl', function() {
});

presentControllers.controller('CandyListCtrl', ["$scope", "$location", "$anchorScroll", function($scope, $location, $anchorScroll) {
   $scope.$on('$viewContentLoaded', function(){
      $("div#sticky").sticky({ topSpacing: 0 });
    });

   $scope.scrollTo = function(id) {
      $location.hash(id);
      $anchorScroll();
   }
}]);

presentControllers.controller('CandyAddCtrl', ["$scope", function($scope) {
}]);

presentControllers.controller('CandyEditCtrl', ["$scope", function($scope) {
}]);

presentControllers.controller('PresentAddCtrl', ["$scope", "$location", "$anchorScroll", function($scope, $location, $anchorScroll) {
   $scope.$on('$viewContentLoaded', function(){
      $("div#sticky").sticky({ topSpacing: 0 });
    });

   $scope.scrollTo = function(id) {
      $location.hash(id);
      $anchorScroll();
   }
}]);

presentControllers.controller('PresentListCtrl', ["$scope", "$location", "$anchorScroll", function($scope, $location, $anchorScroll) {
   $scope.$on('$viewContentLoaded', function(){
      $("div#sticky").sticky({ topSpacing: 0 });
    });

   $scope.scrollTo = function(id) {
      $location.hash(id);
      $anchorScroll();
   }
}]);

presentControllers.controller('PresentShowCtrl', ["$scope", function($scope) {
}]);

