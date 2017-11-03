/**
 * Controller for the modal popup when creating
 * a new dashboard on the startup page
 */
(function () {
    'use strict';

    angular
        .module(HygieiaConfig.module)
        .controller('EditPropertyController', EditPropertyController);

    EditPropertyController.$inject = ['propertyItem', 'propertyManager'];
    function EditPropertyController(propertyItem, propertyManager) {

        var ctrl = this;

        // public variables
        ctrl.collectorItemProperties = propertyItem;

        // public methods
        ctrl.getPropertyItemList = getPropertyItemList;
        ctrl.getPropertiesForSelected = getPropertiesForSelected;
        ctrl.addProperty = addProperty;
        ctrl.editProperty = editProperty;
        ctrl.deleteProperty = deleteProperty;




        function getPropertyItemList(filter) {
            return propertyManager.getStoredItemPropertyList({"search": filter, "size": 20}).then(function (response){
                return response;
            });
        }

        function getPropertiesForSelected(propertyType){
            propertyManager.getSelectedItemProperties(propertyType).then(function (response){
                ctrl.collectorItemProperties = response
            })
        }

        function addProperty(form){

            if (form.$valid) {
                var submitData = {
                    name: ctrl.collectorItemProperties.name,
                    propertiesKey: document.addPropertyForm.propertiesKey.value,
                    propertiesValue: document.addPropertyForm.propertiesValue.value
                };
                submitProperty(submitData);
            }

        }
        function editProperty(key, value){
            console.log(value)
            var submitData = {
                name: ctrl.collectorItemProperties.name,
                propertiesKey: key,
                propertiesValue: value
            };
            submitProperty(submitData);
        }
        function deleteProperty(key, value){

            var submitData = {
                name: ctrl.collectorItemProperties.name,
                propertiesKey: key,
                propertiesValue: value
            };
            propertyManager
                .removeProperties(submitData)
                .success(function (data) {
                    getPropertiesForSelected(data.name)
                })
                .error(function (data) {

                });
        }
        function submitProperty(submitData){


            propertyManager
                .updateProperties(submitData)
                .success(function (data) {
                    getPropertiesForSelected(data.name)
                })
                .error(function (data) {

                });
        }
    }
})();
