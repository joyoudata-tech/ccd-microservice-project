'use strict';

angular.module('oauthApp', ['ui.router','xeditable','ui.bootstrap'])
    .config(function ($stateProvider, $httpProvider, $urlRouterProvider) {
    	$urlRouterProvider.otherwise('/');
    	$stateProvider
    	.state('home', {
            url: '/',
    		templateUrl: 'ui/views/home.html',
            controller: 'homeCtrl'
        }).state('user', {
            url: '/user',
        	templateUrl: 'ui/views/user.html',
            controller: 'userCtrl',
            controllerAs: 'userController'
        }).state('project', {
            url: '/project',
        	templateUrl: 'ui/views/project.list.html',
            controller: 'projectCtrl',
            controllerAs: 'projectController'
        }).state('newject',{
        	url: '/newject',
        	templateUrl: 'ui/views/project.newject.html',
        	controller: 'projectCtrl',
        	controllerAs: 'projectController'
        }).state('projectDetails',{
        	url: '/project/:projectId',
        	templateUrl: 'ui/views/project.details.html',
        	controller: 'oneprojectCtrl',
        	controllerAs: 'oneprojectController'
        }).state('contract', {
            url: '/contract',
        	templateUrl: 'ui/views/home.html',
            controller: 'homeCtrl'
        }).state('returnproject', {
            url: '/returnproject',
        	templateUrl: 'ui/views/project.html',
            controller: 'newprojectController'
        });

        //Custom header is needed starting angular 1.3; else Spring security might pop authentication dialog
        // by sending the WWW-Authenticate header field in the 401 Unauhorized HTTP response
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .directive("projectDetails", function () {
        return {
            restrict: 'E',
            templateUrl: "ui/views/project-details.html"
        };
    }).directive('toggle', function(){
    	return {
		    restrict: 'A',
		    link: function(scope, element, attrs){
		      if (attrs.toggle=="tooltip"){
		        $(element).tooltip();
		      }
		      if (attrs.toggle=="popover"){
		        $(element).popover();
		      }
		    }
		  };
	}).filter('saleworkDaysSum', function() {
		return function (data, key) {			
			//not typeof(data) === undefined 
			if(typeof(data) === typeof(undefined) || typeof(key) === typeof(undefined)) {
				return 0;
			}			
			var sum = 0;
			var i = data.length -1;
			for (; i >= 0; i--) {
				sum += parseInt(data[i][key]);
			}
			return sum;
		}
	});
