(function() {
    var graph = function(container, json) {
        graph.render(container, json);
    }
    graph.render = function(container, json){
        var myDiv = document.createElement('div');
        PageBuilder.setAttribute(myDiv, 'class', json.classes);
        PageBuilder.setAttribute(myDiv, 'id', json.id);
        container.append(myDiv);
        var view = new app.GraphView($('#'+json.id), $('#'+json.legendId));
        view.render();
    }
    PageBuilder.extensions.graph = graph;
})();