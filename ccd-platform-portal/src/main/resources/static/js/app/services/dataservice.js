'use strict';

/**
 * @ngdoc service
 * @name alertApp.dataService
 * @description
 * # dataService
 * Factory in the alertApp.
 */
angular.module('oauthApp')
    .factory('dataService', function ($http, $q) {
    	
        var userApi = '/uaa/users';

        var projectApi = '/project-service/projects';
        
        var saleworkingApi = '/project-service/saleworkings';

        var loggedInUserApi = '/api/loggedinuser/me';
        
        var makeRestCaller = function (url, method, data) {
        	switch (method){
        	case 'GET' :
        		return $http.get(url).then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }
                }, function (response) {
                    return $q.reject(response.data);
                });
        		break;
        	case 'POST' :
        		var d = data;
        		return $http.post(url, data).then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }
                }, function (response) {
                    return $q.reject(response.data);
                });
        		break;
        	case 'DELETE' : 
        		return $http.delete(url).then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }
                }, function (response) {
                    return $q.reject(response.data);
                });
        		break;
        	case 'PUT' :
        		return $http.put(url,data).then(function (response) {
                    if (typeof response.data === 'object') {
                        return response.data;
                    } else {
                        return $q.reject(response.data);
                    }
                }, function (response) {
                    return $q.reject(response.data);
                });
        		break;
        	}
        }

        return {
            getAllUserData: function () {	
                return makeRestCaller(userApi, 'GET', '');
            },

            getAllProjectData: function () {     
                return makeRestCaller(projectApi, 'GET', '');
            },

            getUserDataByUserName: function (userName) {
                return makeRestCaller(userApi + '/' + userName, 'GET', '');
            },

            getProjectDataByProjectId: function (projectId) {        
                return makeRestCaller(projectApi + '/' + projectId, 'GET', '');
            },
            
            getAllSaleWorkingDataByProjectId: function (projectId) {
            	return makeRestCaller(saleworkingApi, 'GET', '');
            },

            getLoggedInUser: function () {
                return makeRestCaller(loggedInUserApi, 'GET', '');
            },
            
            getAllProjectDepartmentsItems : function () {
            	return makeRestCaller(projectApi + '/' + 'departmentItems', 'GET', '');
            },
            
            getAllProjectSchedulesItems : function () {
            	return makeRestCaller(projectApi + '/' + 'scheduleItems', 'GET', '');
            },
            
            submitProjectFrom: function(data){
            	return makeRestCaller(projectApi, 'POST', data);
            },
            
            updataProjectById: function(projectId, data){
            	return makeRestCaller(projectApi + '/' + projectId, 'PUT', data);
            },
            
            submitSaleWorkingForm: function(data){
            	return makeRestCaller(saleworkingApi, 'POST', data);
            },
            
            deleteOneProject: function(projectName){
            	return makeRestCaller(projectApi + '/' + projectName, 'DELETE', '');
            }
        };
    });