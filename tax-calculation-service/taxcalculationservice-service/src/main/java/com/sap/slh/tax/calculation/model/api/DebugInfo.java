package com.sap.slh.tax.calculation.model.api;

import java.util.HashMap;
import java.util.Map;

public class DebugInfo {

	private Map<String, Object> debugInfo;

	public DebugInfo(DebugInfoBuilder debugInfoBuilder) {
		this.debugInfo = debugInfoBuilder.debugInfo;
	}

	public static class DebugInfoBuilder {

		private Map<String, Object> debugInfo = null;

		public DebugInfoBuilder() {
			debugInfo = new HashMap<String, Object>();
		}

		public DebugInfoBuilder put(String key, Object value) {
			debugInfo.put(key, value);
			return this;
		}

		public Map<String, Object> build() {
			return new DebugInfo(this).getDebugInfo();
		}
	}

	public Map<String, Object> getDebugInfo() {
		return debugInfo;
	}
}
