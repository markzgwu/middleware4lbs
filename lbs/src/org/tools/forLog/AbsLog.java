package org.tools.forLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbsLog{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
}
