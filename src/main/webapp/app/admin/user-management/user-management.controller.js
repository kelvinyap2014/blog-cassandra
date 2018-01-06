(function() {
    'use strict';

    angular
        .module('cblogApp')
        .controller('UserManagementController', UserManagementController);

    UserManagementController.$inject = ['Principal', 'User', 'ParseLinks', 'AlertService'];

    function UserManagementController(Principal, User, ParseLinks, AlertService) {
        var vm = this;

        vm.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        vm.currentAccount = null;
        vm.languages = null;
        vm.loadAll = loadAll;
        vm.setActive = setActive;
        vm.users = [];
        

        vm.loadAll();
        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        function setActive (user, isActivated) {
            user.activated = isActivated;
            User.update(user, function () {
                vm.loadAll();
                vm.clear();
            });
        }

        function loadAll () {
            User.query(onSuccess, onError);
        }

        function onSuccess(data, headers) {
            
            vm.users = data;
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }

    }
})();
