//标题栏
Vue.component('l-brand', {
    props: ['href'],
    template: "<div id='header'><h1><a v-bind:href='href'></a></h1></div>"
})
//搜索框
Vue.component('l-search', {
    props: ['placeholder', 'action', 'method', 'queryType'],
    methods: {
        queryData: function (url, method, queryType) {
            this.data.input = this.message;

            switch (queryType) {
                case "json":
                    $.ajax({
                        type: method,
                        contentType: "application/json",
                        url: url,
                        data: JSON.stringify(this.data),
                        success: function (result) {

                        }
                    });
                    break;
                default:
                    $.ajax({
                        type: method,
                        traditional: true,
                        data: this.data,
                        url: url,
                        success: function (result) {

                        }
                    });
                    break;
            }
        }
    },
    data: function () {
        return {
            data: {},
            message: ''
        }
    },
    template: '<div id="search"><input type="text" v-model="message" v-bind:placeholder="placeholder"/><button v-on:click="queryData(action,method,queryType)" style="cursor: pointer" class="tip-bottom"><i class="icon-search icon-white"></i></button></div>'
})

//下拉菜单的一项
Vue.component('l-drop-menu-item', {
    props: ['title', 'href', 'icon'],
    template: '<li><a v-bind:href="href"><i v-bind:class="icon"></i>{{title}}</a></li>'
})
//分隔线
Vue.component('l-drop-menu-divider', {
    template: '<li class="divider"></li>'
})

//下拉菜单按钮
Vue.component('l-drop-menu-btn', {
    props: ['id', 'icon', 'title'],
    template: '<li class="dropdown" v-bind:id="id">' +
        '<a href="#" data-toggle="dropdown" v-bind:data-target="id" class="dropdown-toggle">' +
        '<i v-bind:class="icon" style="margin-right: 5px"></i><span class="text">{{title}}</span><b class="caret"></b>' +
        '</a> ' +
        '<ul class="dropdown-menu"><slot></slot></ul>' +
        '</li>'
})
//普通菜单按钮
Vue.component('l-menu-btn', {
    props: ['id', 'icon', 'title', 'href'],
    template: '<li v-bind:id="id">' +
        '<a href="href">' +
        '<i v-bind:class="icon" style="margin-right: 5px"></i><span class="text">{{title}}</span>' +
        '</a> ' +
        '</li>'
})
//导航栏
Vue.component('l-navigator', {
    template: '<div id="user-nav" class="navbar navbar-inverse" style="z-index: 100"><ul class="nav"><slot></slot></ul></div>'
})
/**
 * sidebar
 */
//侧边栏container
Vue.component('l-sidebar', {
    template: '<div id="sidebar"><ul><slot></slot></ul></div>'
})
//侧边栏按钮
Vue.component('l-side-btn', {
    props: ['isactive', 'href', 'icon', 'title'],
    data: function () {
        return {
            isActive: false
        }
    },
    created: function () {
        if (this.isactive === 'true') {
            this.isActive = true;
        }
    },
    template: '<li v-bind:class="{active:isActive}"><a v-bind:href="href"><i v-bind:class="icon"></i> <span>{{title}}</span></a> </li>'
})
//侧边栏下拉按钮
Vue.component('l-side-drop-btn', {
    props: ['isActive', 'icon', 'title'],
    data: function () {
        return {
            isActive: false
        }
    },
    created: function () {
        if (this.isactive === 'true') {
            this.isActive = true;
        }
    },
    template: '<li class="submenu" v-bind:class="{active:isActive}"><a href="#"><i v-bind:class="icon"></i><span>{{title}}</span></a><ul><slot></slot></ul></li>'

})
//侧边栏下拉项
Vue.component('l-side-drop-item', {
    props: ['href', 'icon', 'title'],
    template: '<li><a v-bind:href="href"><i v-bind:class="icon" style="margin-right: 5px"></i><span>{{title}}</span></a></li>'
})
/**
 * container
 */
Vue.component('l-container', {
    props: ['size'],
    data: function () {
        return {
            width: ''
        }
    },
    created: function () {
        switch (this.size) {
            case "12":
                this.width = "span12"
                break;
            case "8":
                this.width = "span8"
                break;
            case "6":
                this.width = "span6"
                break;
            case "4":
                this.width = "span4"
                break;
            case "3":
                this.width = "span3"
                break;
        }
    },
    template: '<div v-bind:class="width" class="container-fluid" ><slot></slot></div>'
})

Vue.component('l-content', {
    props: ['title'],
    template: '<div id="content" style="min-height: 700px"><div id="content-header"><div id="breadcrumb"  ><slot name="router"></slot></div><h1>{{title}}</h1></div><slot></slot></div>'
})
Vue.component('l-router', {
    props: ['href', 'iscurrent', 'title'],
    data: function () {
        return {
            isCurrent: false
        }
    },
    created: function () {
        if (this.iscurrent === 'true') {
            this.isCurrent = true;
        }
    },
    template: '<a v-bind:href="href" v-bind:class="{current:isCurrent}">{{title}}</a>'
})

Vue.component('l-accordion-container', {
    props: ['title', 'id', 'icon'],
    data: function () {
        return {
            href: ''
        }
    },
    created: function () {
        this.href = '#' + this.id;
    },
    template: '<div class="accordion-group widget-box">' +
        '<div class="accordion-heading">' +
        '<div class="widget-title"><a data-parent="#collapse-group" v-bind:href="href" data-toggle="collapse"><span class="icon"><i v-bind:class="icon"></i></span><h5>{{title}}</h5></a></div></div>' +
        '<div class="collapse in accordion-body" v-bind:id="id"><slot></slot></div>' +
        '</div>'
})

Vue.component('l-widget-container', {
    props: ['title', 'icon'],
    template: '<div class="widget-box"><div class="widget-title"><span class="icon"><i v-bind:class="icon"></i></span><h5>{{title}}</h5></div><div class="widget-content nopadding"><slot></slot></div></div>'
})

/**
 * tab
 */
Vue.component('l-tab-container', {
    template: ' <div class="widget-box">' +
        '<div class="widget-title">' +
        '<ul class="nav nav-tabs"><slot name="nav"></slot></ul>' +
        '</div>' +
        '<div class="widget-content tab-content"><slot name="content"></slot></div>'
})
Vue.component('l-tab-nav', {
    props: ['isactive', 'title', 'href'],
    data: function () {
        return {
            isActive: false
        }
    },
    created: function () {
        if (this.isactive === 'true') {
            this.isActive = true;
        }
    },
    template: '<li v-bind:class="{active:isActive}"><a data-toggle="tab" v-bind:href="href">{{title}}</a></li>'
})
Vue.component('l-tab-content', {
    props: ['isactive', 'id'],
    data: function () {
        return {
            isActive: false
        }
    },
    created: function () {
        if (this.isactive === 'true') {
            this.isActive = true;
        }
    },
    template: '<div v-bind:id="id" class="tab-pane" v-bind:class="{active:isActive}"><slot></slot></div>'
})

Vue.component('l-form', {
    template: '<form class="form-horizontal"><slot></slot></form>'
})
Vue.component('l-form-custom', {
    props: ['title'],
    template: '<div class="control-group"><label class="control-label">{{title}}</label><div class="controls"><slot></slot></div></div>'
})
Vue.component('l-form-input', {
    props: ['title', 'placeholder', 'id', 'type', 'size','value'],
    data: function () {
        return {
            data_size: ''
        }
    },
    created: function () {
        this.data_size = 'span' + this.size;
    },
    template: ' <div class="control-group"><label class="control-label">{{title}}</label><div class="controls"><input v-bind:id="id" v-bind:type="type" v-bind:class="data_size" v-bind:placeholder="placeholder" v-bind:value="value"/></div></div>'
})
Vue.component('l-form-btn-container', {
    template: '<div class="form-actions"><slot></slot></div>'
})

/**
 * element
 */
Vue.component('l-quick-action', {
    template: '<ul class="quick-actions"><slot></slot></ul>'
})
Vue.component('l-quick-btn', {
    props: ['color', 'href', 'icon', 'title'],
    data: function () {
        return {
            data_color: 'bg_lb'
        }
    },
    created: function () {
        switch (this.color) {
            case 'green':
                this.data_color = 'bg_lg'
                break;
            case 'yellow':
                this.data_color = 'bg_ly'
                break;
            case 'blue':
                this.data_color = 'bg_lb'
                break;
            case 'red':
                this.data_color = 'bg_lo'
                break;
            case 'dark-blue':
                this.data_color = 'bg_ls'
                break;
        }
    },
    template: '<li v-bind:class="data_color"><a v-bind:href="href"><i v-bind:class="icon"></i>{{title}}</a></li>'
})

Vue.component('l-label', {
    props: ['href', 'title', 'color'],
    data: function () {
        return {
            data_color: ''
        }
    },
    created: function () {
        switch (this.color) {
            case 'gray':
                this.data_color = ''
                break;
            case 'green':
                this.data_color = 'label-success'
                break;
            case 'yellow':
                this.data_color = 'label-warning'
                break;
            case 'blue':
                this.data_color = 'label-info'
                break;
            case 'red':
                this.data_color = 'label-important'
                break;
            case 'black':
                this.data_color = 'label-inverse'
                break;
        }
    },
    template: '<a v-bind:href="href" class="label" v-bind:class="data_color" style="margin: 10px;cursor: pointer">{{title}}</a>'
})

Vue.component('l-notification', {
    props: ['color', 'title'],
    data: function () {
        return {
            data_color: ''
        }

    },
    created: function () {
        switch (this.color) {
            case 'green':
                this.data_color = 'alert-success'
                break;
            case 'red':
                this.data_color = 'alert-error'
                break;
            case 'blue':
                this.data_color = 'alert-info'
                break;
        }

    },
    template: '<div class="alert" v-bind:class="data_color" style="margin-top: 20px"><button class="close" data-dismiss="alert">×</button><strong>{{title}}</strong></div>'
})