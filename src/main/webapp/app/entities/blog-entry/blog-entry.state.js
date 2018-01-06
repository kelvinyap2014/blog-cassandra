(function() {
    'use strict';

    angular
        .module('cblogApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('blog-entry', {
            parent: 'entity',
            url: '/blog-entry',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BlogEntries'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/blog-entry/blog-entries.html',
                    controller: 'BlogEntryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('blog-entry-detail', {
            parent: 'blog-entry',
            url: '/blog-entry/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'BlogEntry'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/blog-entry/blog-entry-detail.html',
                    controller: 'BlogEntryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'BlogEntry', function($stateParams, BlogEntry) {
                    return BlogEntry.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'blog-entry',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('blog-entry-detail.edit', {
            parent: 'blog-entry-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blog-entry/blog-entry-dialog.html',
                    controller: 'BlogEntryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BlogEntry', function(BlogEntry) {
                            return BlogEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('blog-entry.new', {
            parent: 'blog-entry',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blog-entry/blog-entry-dialog.html',
                    controller: 'BlogEntryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                blog: null,
                                title: null,
                                user: null,
                                content: null,
                                blogDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('blog-entry', null, { reload: 'blog-entry' });
                }, function() {
                    $state.go('blog-entry');
                });
            }]
        })
        .state('blog-entry.edit', {
            parent: 'blog-entry',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blog-entry/blog-entry-dialog.html',
                    controller: 'BlogEntryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BlogEntry', function(BlogEntry) {
                            return BlogEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('blog-entry', null, { reload: 'blog-entry' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('blog-entry.delete', {
            parent: 'blog-entry',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blog-entry/blog-entry-delete-dialog.html',
                    controller: 'BlogEntryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BlogEntry', function(BlogEntry) {
                            return BlogEntry.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('blog-entry', null, { reload: 'blog-entry' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
