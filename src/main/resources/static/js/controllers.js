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
        console.log(error);
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
        console.log(error);
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
        console.log(error);
        alert("Ошибка");
      });
    }
  }
});

presentControllers.controller('PresentAddCtrl', function($scope, $location, $anchorScroll, $route, $routeParams, $q, validationService, Candy, Present) {
  initSticky($scope, $location, $anchorScroll);

  $scope.candies = Candy.query();
  $scope.selectedCandies = [];
  $scope.totalCount = 0;
  $scope.totalPrice = 0.0;

  $scope.computeStatistics = function() {
    $scope.totalCount = $scope.selectedCandies.reduce(function(sum, candy) {return sum + candy.count}, 0);
    $scope.totalPrice = $scope.selectedCandies.reduce(function(sum, candy) {return sum + candy.price * candy.count}, 0).toFixed(2);
  }

  function findById(id, xs) {
    return xs.filter(function(x) {return x.id === id})[0];
  }

  if ($routeParams.presentId) {
    var presentPromise = Present.get({presentId: $routeParams.presentId}).$promise;

    $q.all([$scope.candies.$promise, presentPromise]).then(
      function(data) {
        var candies = data[0];
        var present = data[1];

        candies.forEach(function(candy) {
          var selected = findById(candy.id, present.candies);

          if (selected) {
            candy.checked = true;
            candy.count = selected.count;
          } else {
            candy.checked = false;
          }
        });

        $scope.selectedCandies = present.candies;
        $scope.computeStatistics();
      }
    );
  }

  $scope.checkCandy = function (id, $event) {
    if ($event.target.tagName === "INPUT" && !$event.target.disabled) {
      return;
    }

    var candy = findById(id, $scope.candies);

    if (candy.checked) {
      $scope.selectedCandies = $scope.selectedCandies.filter(function(c) {return c.id !== id});
    } else {
      $scope.selectedCandies.push(candy);
    }

    candy.checked = !candy.checked;
    $scope.computeStatistics();
  }

  $scope.submitForm = function() {
    if (new validationService().checkFormValidity($scope.addPresentForm)) {
      var present = $scope.present;
      present.candies = $scope.selectedCandies;

      Present.save(present).$promise.then(function() {
        $location.path("/present-add");
        $route.reload();
      }, function(error) {
        console.log(error);
        alert("Ошибка");
      });
    }
  }
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
          console.log(error);
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

