'use strict';

/* App Module */

var presentApp = angular.module('presentApp', [
  'ngRoute',
  'presentControllers',
  'presentServices',
  'presentRoutes',
  'presentDirectives',
  'ghiscoding.validation', 'pascalprecht.translate'
]);

presentApp.config(function ($translateProvider) {
  $translateProvider.useSanitizeValueStrategy('escapeParameters');
  $translateProvider.useStaticFilesLoader({
    prefix: '/components/angular-validation-ghiscoding/locales/validation/',
    suffix: '.json'
  });

  // define translation maps you want to use on startup
  $translateProvider.preferredLanguage('ru');
});


