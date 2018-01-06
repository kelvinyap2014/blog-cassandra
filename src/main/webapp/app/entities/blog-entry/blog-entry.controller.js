(function() {
    'use strict';

    angular
        .module('cblogApp')
        .controller('BlogEntryController', BlogEntryController);

    BlogEntryController.$inject = ['BlogEntry'];

    function BlogEntryController(BlogEntry) {

        var vm = this;

        vm.blogEntries = [];

        loadAll();

        function loadAll() {
            BlogEntry.query(function(result) {
                vm.blogEntries = result;
                vm.searchQuery = null;
            });
        }
    }
})();
