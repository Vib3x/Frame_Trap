package com.frametrap.app

class MoveModel(private val prop_movename: String, private val prop_startup: String, private val prop_active: String, private val prop_recovery: String, private val prop_onBlock:String) {
    var movename: String = this.prop_movename
    var startup: String = this.prop_startup
    var active: String = this.prop_active
    var recovery: String = this.prop_recovery
    var onBlock: String = this.prop_onBlock
}