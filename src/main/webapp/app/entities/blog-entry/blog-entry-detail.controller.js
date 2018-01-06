(function() {
    'use strict';

    angular
        .module('cblogApp')
        .controller('BlogEntryDetailController', BlogEntryDetailController);

    BlogEntryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'BlogEntry'];

    function BlogEntryDetailController($scope, $rootScope, $stateParams, previousState, entity, BlogEntry) {
        var vm = this;

        vm.blogEntry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cblogApp:blogEntryUpdate', function(event, result) {
            vm.blogEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
