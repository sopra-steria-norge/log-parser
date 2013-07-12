$.get = function(request) {
    var req = $.extend({type: 'GET'}, request);
    if (typeof Fixtures !== 'undefined'){
    	req = $.extend(req, {fixture:Fixtures.page});
    }
    return $.ajax(req);
}
$.post = function(request) {
    var req = $.extend({type: 'POST'}, request);
        if (typeof Fixtures !== 'undefined'){
        	req = $.extend(req, {fixture:Fixtures.page});
        }
    return $.ajax(req);
}