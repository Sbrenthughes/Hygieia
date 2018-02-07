(function () {
    'use strict';

    angular
        .module(HygieiaConfig.module + '.core')
        .factory('propertyManager', propertyManager);

    function propertyManager($http) {
        var storedPropertyListRoute = '/api/propertyManager/propertyList/';
        var selectedPropertyRoute = '/api/propertyManager/getSelectedProperty/';
        var updatePropertyRoute = '/api/propertyManager/updateProperties/';
        var removePropertyRoute = '/api/propertyManager/removePropertyItem/';
        var propertySelectionListRoute = '/api/propertyManager/collectorSelectionForCreate/'
        return {
            getStoredItemPropertyList: getStoredItemPropertyList,
            getSelectedItemProperties: getSelectedItemProperties,
            updateProperties: updateProperties,
            removeProperties: removeProperties,
            getCollectorSelectionForPropertyCreate: getCollectorSelectionForPropertyCreate
        };

        function getStoredItemPropertyList(){
            return $http.get(storedPropertyListRoute).then(function (response) {
                return response.data;
            });
        }
        function getSelectedItemProperties(data){
            return $http.get(selectedPropertyRoute, {params: data}).then(function (response) {
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
        function getCollectorSelectionForPropertyCreate(params){
            console.log(params)
            return $http.get(propertySelectionListRoute, {params: params}).then(function (response) {
                return response.data;
            });
        }
    }
})();