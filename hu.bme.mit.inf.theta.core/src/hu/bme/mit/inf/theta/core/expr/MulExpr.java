package hu.bme.mit.inf.theta.core.expr;

import java.util.Collection;

import hu.bme.mit.inf.theta.core.type.closure.ClosedUnderMul;

public interface MulExpr<ExprType extends ClosedUnderMul> extends MultiaryExpr<ExprType, ExprType> {
	
	@Override
	public MulExpr<ExprType> withOps(final Collection<? extends Expr<? extends ExprType>> ops);
}