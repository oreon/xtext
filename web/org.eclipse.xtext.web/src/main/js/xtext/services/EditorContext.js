/*******************************************************************************
 * Copyright (c) 2015 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

define(function() {
	
	function EditorContext(editor) {
		this._editor = editor;
		var text = editor.getTextView().getText();
		this._serverState = {};
		this._serverStateListeners = [];
		this._clientServiceState = {};
	};

	EditorContext.prototype = {
		
		getEditor : function() {
			return this._editor
		},
		
		getServerState : function() {
			return this._serverState;
		},
		
		updateServerState : function(currentText, currentStateId) {
			this._serverState.text = currentText;
			this._serverState.stateId = currentStateId;
			return this._serverStateListeners;
		},
		
		addServerStateListener : function(listener) {
			this._serverStateListeners.push(listener);
		},
		
		getClientServiceState : function() {
			return this._clientServiceState;
		},
		
		clearClientServiceState : function() {
			this._clientServiceState = {};
		},
		
		getCaretOffset : function() {
			return this._editor.getTextView().getCaretOffset();
		},
		
		getLineStart : function(lineNumber) {
			var view = this._editor.getTextView();
			return view.getLineStart(view.getLineAtOffset(view.getCaretOffset()));
		},
		
		getSelection : function() {
			return this._editor.getTextView().getSelection();
		},
		
		getText : function(start, end) {
			return this._editor.getTextView().getText(start, end)
		},
		
		isDirty : function() {
			return this._editor.isDirty();
		},
		
		markClean : function(clean) {
			if (clean === undefined || clean) {
				this._editor.markClean();
			} else {
				this._editor.setDirty(true);
			}
		},
		
		setCaretOffset : function(offset, show) {
			this._editor.getTextView().setCaretOffset(offset, show);
		},
		
		setSelection : function(selection) {
			this._editor.getTextView().setSelection(selection.start, selection.end);
		},
		
		setText : function(text, start, end) {
			this._editor.getTextView().setText(text, start, end);
			var fullText = this.getText();
		},
		
		showMarkers : function(problems) {
			this._editor.showProblems(problems);
		}
	};
	
	return EditorContext;
});