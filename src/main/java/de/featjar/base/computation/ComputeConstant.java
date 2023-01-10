package de.featjar.base.computation;

import de.featjar.base.data.Result;
import de.featjar.base.tree.structure.ALeafNode;
import de.featjar.base.tree.structure.ITree;
import java.util.Objects;

/**
 * A constant computation.
 * Always computes the same value.
 * The leaves of a computation tree are precisely its constant computations.
 *
 * @param <T> the type of the computed value
 * @author Elias Kuiter
 */
public class ComputeConstant<T> extends ALeafNode<IComputation<?>> implements IComputation<T> {
    protected final T value;

    /**
     * Creates a constant computation.
     *
     * @param value   the value
     */
    public ComputeConstant(T value) {
        this.value = Objects.requireNonNull(value, "constant computation of null not allowed");
    }

    @Override
    public Result<T> compute(DependencyList dependencyList, Progress progress) {
        return Result.of(value);
    }

    @Override
    public boolean equalsNode(IComputation<?> other) {
        return getClass() == other.getClass()
                && Objects.equals(value, ((ComputeConstant<?>) other).value); // todo:monitor?
    }

    @Override
    public int hashCodeNode() {
        return Objects.hash(getClass(), value); // todo: monitor?
    }

    @Override
    public ITree<IComputation<?>> cloneNode() {
        return new ComputeConstant<>(value);
    }

    @Override
    public String toString() {
        return String.format(
                "%s(%s, %s)", getClass().getSimpleName(), value.getClass().getSimpleName(), value);
    }
}
