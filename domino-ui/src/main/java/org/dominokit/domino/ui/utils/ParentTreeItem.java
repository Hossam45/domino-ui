package org.dominokit.domino.ui.utils;

import org.dominokit.domino.ui.tree.Tree;

import java.util.Optional;

public interface ParentTreeItem<T> {
    T getActiveItem();
    void setActiveItem(T activeItem);
    void setActiveItem(T activeItem, boolean silent);
    Tree getTreeRoot();
    boolean isAutoExpandFound();
    ParentTreeItem expand();
    ParentTreeItem expand(boolean expandParent);
    void activate();
    void activate(boolean activateParent);
    Optional<T> getParent();
}
