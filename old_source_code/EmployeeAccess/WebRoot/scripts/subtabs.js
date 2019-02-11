var Subtabs = function() {

	var defaultPadding = 4;
	var defaultSpeed = 100;

	function scrollTop(element) {
		if (document.all) {
			return 0;
		}

		if (element == document) {
			return 0;
		}
		return element.scrollTop + scrollTop(element.parentNode);
	}

	function scrollWidth(element) {
		if (document.all) {
			return 0;
		}

		if (element == document) {
			return 0;
		}
		return element.scrollLeft + scrollWidth(element.parentNode);
	}

	function createSubtabs() {

		var node = this;
		var isOpen = false;

		var containingMenu = this.getAttribute("containingmenu");

		var lastTrigger = null;
		var subtabsClicksDisabled = false;

		this.closeSubtabs = function() {
			if (lastTrigger) {
				lastTrigger.closeSubtabs();
			}
		}
		
		this.toggleSubtabs = function(trigger) {

//			if (containingMenu && !popupClicksDisabled) {
//				Menu.disablePopupClicks(containingMenu);
//				popupClicksDisabled = true;
//			}

			//$(node).slideToggle(defaultSpeed);
			//$(node).toggle();

//			if ($(node).css("display") != "none" && trigger.getParent() != null) {
//				popupResize();
//
//				repositionBigPopup();
//				repositionPopup();
//			}
//			
//			if ($(node).css("display") != "none") {
//				setFocus();
//			}
//			$(node).find(".subtabComponent").each( function() {
//				if (!$(this).hasClass("disabled")) {
//					$(this).addClass("disabled");
//				} else {
//					$(this).removeClass("disabled");
//				}
//			});
			
			$(node).toggleClass("hidden");
			
			isOpen = !isOpen;
			
//			if (containingMenu) {
//				Menu.toggleMenuGroup(containingMenu);
//			}
		}

		function setFocus() {
			if ($(node).hasClass("drop_down_subtabs")) {
				return;
			}

			var firstElement = $("input:visible, select:visible, a:visible", node)[0];
			if (firstElement != null) {
				try {
					firstElement.focus();
					firstElement.select();
				} catch (e) {
				}
			}
		}

		var subtabsClosersInitialized = false;
		function initializeSubtabsClosers() {
			if (!subtabsClosersInitialized) {
				$(".subtabs_closer", this).click(triggerCloseSubtabs);
			}
		}

		this.isOpen = function() {
			return isOpen;
		}

		var height = new LazyQuery( function() {
			return $(node).height();
		});

		this.getCachedHeight = function() {
			return height.getValue();
		}

		//$(this).removeClass("hidden").hide();
		$(this).addClass("hidden");

		// $(this).appendTo("body");
	}

	function triggerCloseSubtabs() {
		$(this).parents(".subtabs")[0].closeSubtabs();
	}

	function createSubtabsTrigger() {

		var node = this;

		var effectGroupName = this.getAttribute("subtabseffectgroup");

		this.hasEffectGroup = function() {
			return effectGroupName;
		}

		var effectGroupItems = null;

		function getEffectGroupItems() {
			if (effectGroupItems === null) {
				effectGroupItems = $(node).parents(".subtabs_style_target");
				if (node.hasEffectGroup()) {
					effectGroupItems = effectGroupItems.add("[" + effectGroupName + "-subtabseffectclass]");
				}
				effectGroupItems.each( function() {
					createEffectGroupItem(this);
				});
			}
			return effectGroupItems;
		}

		var subtab = null;

		this.getSubtabs = function() {
			if (subtab === null) {
				subtab = $("#" + node.getAttribute("subtabstarget"))[0];
			}
			return subtab;
		}

		this.triggerSubtabs = function() {
			node.getSubtabs().toggleSubtabs(this);
			getEffectGroupItems().each(function() { this.setEffect( effectGroupName, node.getSubtabs().isOpen()); });
		}

		this.openSubtabs = function() {
			if (!node.getSubtabs().isOpen()) {
				this.triggerSubtabs();
			}
		}

		this.closeSubtabs = function() {
			if (node.getSubtabs().isOpen()) {
				this.triggerSubtabs();
			}
		}

		$(this).click(
				function() {
					if (this.getAttribute("displaySubtabs") != false && this.getAttribute("displaySubtabs") != "false"
							&& !$(this).hasClass("disabled")) {
						this.triggerSubtabs();
					}

					return false;
				});

		if ($(this).hasClass("displaySubtabs")) {
			this.openSubtabs();
		}
	}

	function createEffectGroupItem(node) {

		function getClass(groupName) {
			var styleName = null;
			if (groupName) {
				styleName = node.getAttribute(groupName + "-subtabseffectclass");
			}
			if (!styleName) {
				styleName = "subtabs_open";
			}
			return styleName;
		}

		node.setEffect = function(groupName, subtabsStatus) {
			if (subtabsStatus) {
				$(this).addClass(getClass(groupName));
			} else {
				$(this).removeClass(getClass(groupName));
			}
		}
	}

	function createCloseSubtabs() {
		$(this).click( function() {
			$(this).parents(".subtabs")[0].closeSubtabs();
			return false;
		});
	}

	return {
		initializeClassBehavior : function() {
			Behaviors.registerClassBehavior("subtabs", createSubtabs);
			Behaviors.registerClassBehavior("subtabs_trigger", createSubtabsTrigger);
			Behaviors.registerClassBehavior("close_subtabs", createCloseSubtabs);
		}
	}

}();

Behaviors.register(Subtabs);
