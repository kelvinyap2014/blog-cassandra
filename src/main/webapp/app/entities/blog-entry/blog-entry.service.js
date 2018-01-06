(function() {
    'use strict';
    angular
        .module('cblogApp')
        .factory('BlogEntry', BlogEntry);

    BlogEntry.$inject = ['$resource', 'DateUtils'];

    function BlogEntry ($resource, DateUtils) {
        var resourceUrl =  'api/blog-entries/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.blogDate = DateUtils.convertDateTimeFromServer(data.blogDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
