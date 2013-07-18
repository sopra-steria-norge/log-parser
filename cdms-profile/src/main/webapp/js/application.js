$(document).ready(function() {
    $.get({
    	//url: 'rest/getLayout/navbar',
        url: 'page/navbar',
        success: function(r) {
            new PageView({model: new PageComponentCollection(JSON.parse(r)), el: 'body'})
        }
    });
    $.get({
    	//url: 'rest/getLayout/last24h',
        url: 'page/last24h',
        success: function(r) {
            new PageView({model: new PageComponentCollection(JSON.parse(r)), el: '.applicationcontainer'})
        }
    });
});