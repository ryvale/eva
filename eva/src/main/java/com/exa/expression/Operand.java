package com.exa.expression;

public interface Operand<T extends XPItem<T>> extends XPItem<T> {

	boolean isConstant();

}