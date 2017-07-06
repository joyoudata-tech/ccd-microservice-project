'use strict';

/**
 * @ngdoc function
 * @name oauthApp.controller:UserCtrl
 * @description
 * # PreviewPlaceHolderCtrl
 * Controller of the alertApp
 */
angular.module('oauthApp')
    .controller('projectCtrl', function ($scope, $location, $http, $state, dataService, $filter) {
    	
        var assignAllProjectDataToScope = function (data) {
            $scope.projectDataArray = data;
        };
               
        var assignProjectDataToScope = function (data) {
            $scope.projectData = data;
        };
        
        var assignProjectSchedulesToScope = function (data) {
        	$scope.projectSchedules = data;
        };
        
        var assignProjectDepartmentsToScope = function (data) {
        	$scope.projectDepartments = data;
        };

        var logError = function (error) {
            console.log(error);
        };
      
        $scope.submitProjectFrom = function () {
        	var data = $scope.formData;
        	data.projectDepartment = angular.fromJson($scope.formData.projectDepartment);
        	data.projectSchedule = angular.fromJson($scope.formData.projectSchedule);
        	dataService.submitProjectFrom(data)
        		.then(assignProjectDataToScope, logError);
        };       
        
        $scope.toProjectDetails = function (projectId) {
        	$state.go('projectDetails', { projectId: projectId});
        }

        this.getProjectDataByProjectId = function (projectId) {
            dataService.getProjectDataByProjectId(projectId)
                .then(assignProjectDataToScope, logError);
        };        
        
        this.deleteOneProject = function (projectName) {
        	dataService.deleteOneProject(projectName);
        };
        
        dataService.getAllProjectData()
            .then(assignAllProjectDataToScope, logError);
        
        dataService.getAllProjectDepartmentsItems()
        	.then(assignProjectDepartmentsToScope, logError);
        
        dataService.getAllProjectSchedulesItems()
    		.then(assignProjectSchedulesToScope, logError);
    });

angular.module('oauthApp')
.controller('oneprojectCtrl', function ($scope, $location, $http, $state, $stateParams, $filter, $modal, dataService) {
	
	var projectId = $stateParams.projectId;
	
	var logError = function (error) {
        console.log(error);
    };
	
	var assignProjectDataToScope = function (data) {
        $scope.projectData = data;
    };
    
    var assignAllSaleWorkingDataByProjectIdToScope = function (data) {
        $scope.saleWorkingDataArray = data;    
    };
    
    var assignSaleWorkingDataToScope = function (data) {
    	$scope.saleWorkingData = angular.toJson(data);
    };
    
    var assignProjectSchedulesToScope = function (data) {
    	$scope.projectSchedules = data;
    };
    
    var assignProjectDepartmentsToScope = function (data) {
    	$scope.projectDepartments = data;
    };
    
    dataService.getAllProjectDepartmentsItems()
		.then(assignProjectDepartmentsToScope, logError);

    dataService.getAllProjectSchedulesItems()
		.then(assignProjectSchedulesToScope, logError);
    
    $scope.updateProject = function() {
    	var data = $scope.projectData;
    	data.projectDepartment = angular.fromJson($scope.projectData.projectDepartment);
    	data.projectSchedule = angular.fromJson($scope.projectData.projectSchedule);
    	dataService.updataProjectById(projectId, data);
    };
    
    this.getAllSaleWorkDataByProjectId = function (projectId) {
    	dataService.getAllSaleWorkingDataByProjectId(projectId)
    	.then(assignAllSaleWorkingDataToScope, logError);
    };
    
    dataService.getProjectDataByProjectId(projectId)
        .then(assignProjectDataToScope, logError);
    
//    var datepicker = $('#p_work_date').datetimepicker({  
//        format: 'YYYY-MM-DD',  
//        locale: moment.locale('zh-cn')  
//    }).on('dp.change', function (e) {  
//        var result = new moment(e.date).format('YYYY-MM-DD');  
//        $scope.formData.saleWorkDate = result;  
//        $scope.$apply();  
//    });
    
    $scope.$watch('projectData.projectDepartment',function(oldVal,newVal){
    	if(newVal !== oldVal){
    		var selected = $filter('filter')($scope.projectDepartments,$scope.projectData.projectDepartment);
    		if(typeof(selected) === typeof(undefind)){
    			return '';
    		}
    		$scope.projectData.projectDepartment && selected.length ? selected[0].departmentName : '没有设置';
    	}
    });
    
    $scope.$watch('projectData.projectSchedule', function(oldVal, newVal) {
    	if (newVal !== oldVal) {
    		var selected = $filter('filter')($scope.projectSchedules, $scope.projectData.projectSchedule);
    		if(typeof(selected) === typeof(undefined)) {
				return '';
			}
    		$scope.projectData.projectSchedule && selected.length ? selected[0].scheduleName : '没有设置';
    	}
    });
    
    $scope.openSaleWorkModal = function () {
        $modal.open({
            templateUrl: 'project.saleworkmodal.html',
            controller: function ($scope, $modalInstance) {
                $scope.submitSaleWorkingForm = function () {
                	$scope.formData.projectId = projectId;
                	var data = $scope.formData;
                	dataService.submitSaleWorkingForm(data)
                		.then(assignProjectDataToScope, logError);
                	$state.transitionTo($state.current, $stateParams, { reload: true, inherit: true, notify: true });
                    $modalInstance.dismiss('cancel');
                };
                $scope.cancel = function () {
                    $modalInstance.dismiss('cancel');
                };
                
                $scope.format = "yyyy/MM/dd";
                $scope.altInputFormats = ['yyyy/M!/d!'];
                $scope.opendate = function ($event) {
                	$event.preventDefault();
                    $event.stopPropagation();
                	$scope.opened = true;
                };
            }
        });
    };
    
    
});
