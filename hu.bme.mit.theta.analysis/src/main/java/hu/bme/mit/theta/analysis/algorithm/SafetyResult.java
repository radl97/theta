package hu.bme.mit.theta.analysis.algorithm;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import hu.bme.mit.theta.analysis.Action;
import hu.bme.mit.theta.analysis.State;
import hu.bme.mit.theta.analysis.Trace;
import hu.bme.mit.theta.common.ObjectUtils;

public abstract class SafetyResult<S extends State, A extends Action> {
	private final ARG<S, A> arg;
	private final Optional<Statistics> stats;

	private SafetyResult(final ARG<S, A> arg, final Optional<Statistics> stats) {
		this.arg = checkNotNull(arg);
		this.stats = checkNotNull(stats);
	}

	public ARG<S, A> getArg() {
		return arg;
	}

	public Optional<Statistics> getStats() {
		return stats;
	}

	public static <S extends State, A extends Action> Safe<S, A> safe(final ARG<S, A> arg) {
		return new Safe<>(arg, Optional.empty());
	}

	public static <S extends State, A extends Action> Unsafe<S, A> unsafe(final Trace<S, A> cex, final ARG<S, A> arg) {
		return new Unsafe<>(cex, arg, Optional.empty());
	}

	public static <S extends State, A extends Action> Safe<S, A> safe(final ARG<S, A> arg, final Statistics stats) {
		return new Safe<>(arg, Optional.of(stats));
	}

	public static <S extends State, A extends Action> Unsafe<S, A> unsafe(final Trace<S, A> cex, final ARG<S, A> arg,
			final Statistics stats) {
		return new Unsafe<>(cex, arg, Optional.of(stats));
	}

	public abstract boolean isSafe();

	public abstract boolean isUnsafe();

	public abstract Safe<S, A> asSafe();

	public abstract Unsafe<S, A> asUnsafe();

	////

	public static final class Safe<S extends State, A extends Action> extends SafetyResult<S, A> {
		private Safe(final ARG<S, A> arg, final Optional<Statistics> stats) {
			super(arg, stats);
			checkArgument(arg.isInitialized(), "ARG is not initialized");
			checkArgument(arg.isComplete(), "ARG is not complete");
			checkArgument(arg.isSafe(), "ARG is not safe");
		}

		@Override
		public boolean isSafe() {
			return true;
		}

		@Override
		public boolean isUnsafe() {
			return false;
		}

		@Override
		public Safe<S, A> asSafe() {
			return this;
		}

		@Override
		public Unsafe<S, A> asUnsafe() {
			throw new ClassCastException(
					"Cannot cast " + Safe.class.getSimpleName() + " to " + Unsafe.class.getSimpleName());
		}

		@Override
		public String toString() {
			return ObjectUtils.toStringBuilder(SafetyResult.class.getSimpleName()).add(Safe.class.getSimpleName())
					.toString();
		}
	}

	public static final class Unsafe<S extends State, A extends Action> extends SafetyResult<S, A> {
		private final Trace<S, A> cex;

		private Unsafe(final Trace<S, A> cex, final ARG<S, A> arg, final Optional<Statistics> stats) {
			super(arg, stats);
			this.cex = checkNotNull(cex);
		}

		public Trace<S, A> getTrace() {
			return cex;
		}

		@Override
		public boolean isSafe() {
			return false;
		}

		@Override
		public boolean isUnsafe() {
			return true;
		}

		@Override
		public Safe<S, A> asSafe() {
			throw new ClassCastException(
					"Cannot cast " + Unsafe.class.getSimpleName() + " to " + Safe.class.getSimpleName());
		}

		@Override
		public Unsafe<S, A> asUnsafe() {
			return this;
		}

		@Override
		public String toString() {
			return ObjectUtils.toStringBuilder(SafetyResult.class.getSimpleName()).add(Unsafe.class.getSimpleName())
					.toString();
		}
	}

}
