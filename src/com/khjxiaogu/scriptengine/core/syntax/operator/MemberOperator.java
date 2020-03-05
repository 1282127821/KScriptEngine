package com.khjxiaogu.scriptengine.core.syntax.operator;

import java.util.List;

import com.khjxiaogu.scriptengine.core.Object.KEnvironment;
import com.khjxiaogu.scriptengine.core.Object.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.Assignable;
import com.khjxiaogu.scriptengine.core.syntax.Visitable;

public interface MemberOperator extends Assignable, Visitable {
	public KEnvironment getSuperEnvironment(KEnvironment env) throws KSException;

	public KVariant getPointing(KEnvironment env) throws KSException;

	public void VisitAsChild(List<String> parentMap);
}
