/**
 * Patch for disappearing nodes in the explorer tree. Issue should be fixed in ZK 8.6.1 - please remove this patch after the update
 */
zk.afterLoad('zul.sel', function () {
	var xTree = {};
	zk.override(zul.sel.Tree.prototype, xTree, {
		onShow: function () {
			this._initPadSizes();
		},
		bind_: function () {
			var result = xTree.bind_.apply(this, arguments);
			zWatch.listen({onShow: this});
		},
		unbind_: function () {
			zWatch.unlisten({onShow: this});
			var result = xTree.unbind_.apply(this, arguments);
		}
	});
});
