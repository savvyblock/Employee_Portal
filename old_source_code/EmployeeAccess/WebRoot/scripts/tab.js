
var Tab = function() {

	var menuGroupStatuses = new Array();
	var menuGroupItems = new Array();

	function getMenuGroupItems(groupName) {
		if (!menuGroupItems[groupName]) {
			menuGroupItems[groupName] = $("[tabgroup='" + groupName + "']");
		}
		return menuGroupItems[groupName];
	}

	function createMenuItem() {
		this.menuGroupName = this.getAttribute("tabgroup");

		if (menuGroupStatuses[this.menuGroupName] === null) {
			menuGroupStatuses[this.menuGroupName] = false;
		}

		this.menuGroupActive = function() {
			return menuGroupStatuses[this.menuGroupName];
		}

		$(this).click(function() {
			menuGroupStatuses[this.menuGroupName] = !menuGroupStatuses[this.menuGroupName];
		});

//		$(this).hover(function() {
//			if (menuGroupStatuses[this.menuGroupName]) {
//				getMenuGroupItems(this.menuGroupName).not(this).each(closeSubtabs);
//				//if (this.openSubtabs) {
//				//	this.openSubtabs();
//				//}
//			}
//		}, function() { });
	}

	function closeSubtabs() {
		if (this.closeSubtabs) {
			this.closeSubtabs();
		}
	}

	return {

		initializeClassBehavior : function() {
			Behaviors.registerClassBehavior("tab_item", createMenuItem);
		},

		activateMenuGroup : function(menuGroup) {
			menuGroupStatuses[menuGroup] = true;
		},

		deactivateMenuGroup : function(menuGroup) {
			menuGroupStatuses[menuGroup] = false;
		},

		toggleMenuGroup : function(menuGroup) {
			menuGroupStatuses[menuGroup] = !menuGroupStatuses[menuGroup];
			if (!menuGroupStatuses[menuGroup]) {
				menuGroupItems[menuGroup].each(closeSubtabs);
			}
		},

		disableSubtabsClicks : function(menuGroup) {
			getMenuGroupItems(menuGroup).filter(".subtabs_trigger")
				.unbind("click")
				.click(function() { return false; });
		}
	}

}();

Behaviors.register(Tab);