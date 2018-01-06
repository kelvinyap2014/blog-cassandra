(function() {
    'use strict';

    angular
        .module('cblogApp')
        .controller('BlogEntryDialogController', BlogEntryDialogController);

    BlogEntryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'BlogEntry'];

    function BlogEntryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, BlogEntry) {
        var vm = this;

        vm.blogEntry = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.blogEntry.id !== null) {
                BlogEntry.update(vm.blogEntry, onSaveSuccess, onSaveError);
            } else {
                BlogEntry.save(vm.blogEntry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cblogApp:blogEntryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.blogDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
