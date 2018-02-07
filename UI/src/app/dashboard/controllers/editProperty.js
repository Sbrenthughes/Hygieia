/**
 * Controller for the modal popup when creating
 * a new dashboard on the startup page
 */
(function () {
    'use strict';

    angular
        .module(HygieiaConfig.module)
        .controller('EditPropertyController', EditPropertyController);

    EditPropertyController.$inject = ['propertyItem', 'propertyManager', 'collectorData'];
    function EditPropertyController(propertyItem, propertyManager, collectorData) {

        var ctrl = this;
        var submitData = {
            name: propertyItem.name,
            collectorType: propertyItem.collectorType,
            propertyKey: "",
            propertyValue: ""
        };
        // public methods
        ctrl.addProperty = addProperty;
        ctrl.editProperty = editProperty;
        ctrl.deleteProperty = deleteProperty;

        (function () {
            collectorData.getCollectorById(propertyItem.id).then(processPropertyResponse)
        })();

        function processPropertyResponse(response) {
            ctrl.collectorItemProperties =  response;
        }
        function addProperty(form){

            if (form.$valid) {
                submitData.propertyKey = document.addPropertyForm.propertiesKey.value;
                submitData.propertyValue = document.addPropertyForm.propertiesValue.value;

                submitProperty(submitData);
            }

        }
        function editProperty(key, value){

            submitData.propertyKey = key;
            submitData.propertyValue = value;

            submitProperty(submitData);
        }
        function deleteProperty(key, value){

            submitData.propertyKey = key;
            submitData.propertyValue = value;

            propertyManager
                .removeProperties(submitData)
                .success(function (data) {
                    collectorData.getCollectorById(data.id).then(processPropertyResponse)
                })
                .error(function (data) {

                });
        }
        function submitProperty(submitData){
            propertyManager
                .updateProperties(submitData)
                .success(function (data) {
                    collectorData.getCollectorById(data.id).then(processPropertyResponse)
                })
                .error(function (data) {

                });
        }
    }
})();
