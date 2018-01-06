(function() {
    'use strict';

    angular
        .module('cblogApp')
        .controller('BlogEntryDeleteController',BlogEntryDeleteController);

    BlogEntryDeleteController.$inject = ['$uibModalInstance', 'entity', 'BlogEntry'];

    function BlogEntryDeleteController($uibModalInstance, entity, BlogEntry) {
        var vm = this;

        vm.blogEntry = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BlogEntry.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
