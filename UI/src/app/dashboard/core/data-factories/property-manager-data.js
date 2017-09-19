(function () {
    'use strict';

    angular
        .module(HygieiaConfig.module + '.core')
        .factory('propertyManager', propertyManager);

    function propertyManager($http) {
        var fileUploadRoute = '/api/propertyManager/propertiesFileUpload/';
        var storedPropertyListRoute = '/api/propertyManager/propertyList/';
        var selectedPropertyRoute = '/api/propertyManager/getSelectedProperty';
        var updatePropertyRoute = '/api/propertyManager/updateProperties/';
        var removePropertyRoute = '/api/propertyManager/removeProperties/'
        return {
            getStoredItemPropertyList: getStoredItemPropertyList,
            uploadFileToUrl: uploadFileToUrl,
            getSelectedItemProperties: getSelectedItemProperties,
            updateProperties: updateProperties,
            removeProperties: removeProperties
        };

        function getStoredItemPropertyList( params){
            return $http.get(storedPropertyListRoute,{params: params}).then(function (response) {
                return response.data;
            });
        }

        function uploadFileToUrl(file){
            var fd = new FormData();
            fd.append('file', file);

            $http.post(fileUploadRoute, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
                .success(function(){
                })
                .error(function(data){
                    console.log(data)
                });
        }
        function getSelectedItemProperties(type){
            return $http.get(selectedPropertyRoute + "/"+type).then(function (response) {
                return response.data;
            });
        }

        function updateProperties(data){
            return $http.post(updatePropertyRoute, data)
                .success(function (response) {
                    return response.data;
                })
                .error(function (response) {
                    return null;
                });
        }
        function removeProperties(data){
            return $http.post(removePropertyRoute, data)
                .success(function (response) {
                    return response.data;
                })
                .error(function (response) {
                    return null;
                });
        }
    }
})();