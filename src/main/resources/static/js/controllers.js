'use strict';

/* Controllers */

var presentControllers = angular.module('presentControllers', []);

var initSticky = function ($scope, $location, $anchorScroll) {
  $scope.$on('$viewContentLoaded', function(){
    $("div#sticky").sticky({ topSpacing: 0 });
  });

  $scope.scrollTo = function(id) {
    var old = $location.hash();
    $location.hash(id);
    $anchorScroll();
    $location.hash(old);
  }
}

presentControllers.controller('IndexCtrl', function() {
});

presentControllers.controller('CandyListCtrl', function($scope, $location, $anchorScroll, $route, Candy) {
  initSticky($scope, $location, $anchorScroll);

  $scope.candies = Candy.query();

  $scope.remove = function(id) {
    if (confirm("Удалить?")) {
      Candy.remove({candyId: id}).$promise.then(function() {
        $scope.candies = $scope.candies.filter(function(c) {
          return c.id !== id;
        });
      }, function(error) {
        alert("Ошибка");
      });
    }
  }
});

presentControllers.controller('CandyAddCtrl', function($scope, $location, validationService, Candy) {
  $scope.submitForm = function() {
    if (new validationService().checkFormValidity($scope.addCandyForm)) {
      Candy.save($scope.candy).$promise.then(function() {
        $location.path("/candy-list");
      }, function(error) {
        alert("Ошибка");
      });
    }
  }
});

presentControllers.controller('CandyEditCtrl', function($scope, $routeParams, $location, validationService, Candy) {
  $scope.candy = Candy.get({candyId: $routeParams.candyId});

  $scope.submitForm = function() {
    if (new validationService().checkFormValidity($scope.modifyCandyForm)) {
      Candy.update($scope.candy).$promise.then(function() {
        $location.path("/candy-list");
      }, function(error) {
        alert("Ошибка");
      });
    }
  }
});

presentControllers.controller('PresentAddCtrl', function($scope, $location, $anchorScroll, Candy, Present) {
  $scope.candies = Candy.query();

  initSticky($scope, $location, $anchorScroll);
});

presentControllers.controller('PresentListCtrl', function($scope, $location, $anchorScroll, Present) {
  initSticky($scope, $location, $anchorScroll);

  $scope.presents = Present.query();

  $scope.remove = function(id) {
      if (confirm("Удалить?")) {
        Present.remove({presentId: id}).$promise.then(function() {
          $scope.presents = $scope.presents.filter(function(c) {
            return c.id !== id;
          });
        }, function(error) {
          alert("Ошибка");
        });
      }
    }
});

presentControllers.controller('PresentShowCtrl', function($scope, $routeParams, Present) {
   Present.get({presentId: $routeParams.presentId}).$promise.then(
      function(present) {
        present.truePrice = present.candies.reduce(function(sum, candy) {return sum + candy.price * candy.count}, 0).toFixed(2);
        $scope.present = present;
      }
    );
});

