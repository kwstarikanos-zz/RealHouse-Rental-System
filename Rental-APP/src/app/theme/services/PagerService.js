(function () {
    'use strict';
    angular.module('BlurAdmin.theme')
        .service('PagerService', PagerService);

    /** @ngInject */
    function PagerService() {
        return {
            GetPager: function (totalItems, currentPage, pageSize) {
                // default to first page
                currentPage = currentPage || 1;

                // default page size is 8
                pageSize = pageSize || 8;

                // calculate total pages
                var totalPages = Math.ceil(totalItems / pageSize);

                var startPage, endPage;
                if (totalPages <= 20) {
                    // less than 10 total pages so show all
                    startPage = 1;
                    endPage = totalPages;
                } else {
                    // more than 10 total pages so calculate start and end pages
                    if (currentPage <= 12) {
                        startPage = 1;
                        endPage = 10;
                    } else if (currentPage + 8 >= totalPages) {
                        startPage = totalPages - 18;
                        endPage = totalPages;
                    } else {
                        startPage = currentPage - 10;
                        endPage = currentPage + 8;
                    }
                }

                // calculate start and end item indexes
                var startIndex = (currentPage - 1) * pageSize;
                var endIndex = Math.min(startIndex + pageSize - 1, totalItems - 1);

                // create an array of pages to ng-repeat in the pager control
                var pages = _.range(startPage, endPage + 1);

                // return object with all pager properties required by the view
                return {
                    totalItems: totalItems,
                    currentPage: currentPage,
                    pageSize: pageSize,
                    totalPages: totalPages,
                    startPage: startPage,
                    endPage: endPage,
                    startIndex: startIndex,
                    endIndex: endIndex,
                    pages: pages
                };
            }
        }
    }
})();
