package org.dominokit.domino.ui.lists;

import com.google.gwt.user.client.TakesValue;
import elemental2.dom.HTMLAnchorElement;
import elemental2.dom.Node;

import org.dominokit.domino.ui.keyboard.KeyboardEvents;
import org.dominokit.domino.ui.style.Color;
import org.dominokit.domino.ui.utils.HasBackground;
import org.dominokit.domino.ui.utils.HasValue;
import org.dominokit.domino.ui.utils.Selectable;
import org.dominokit.domino.ui.utils.Switchable;
import org.jboss.gwt.elemento.core.IsElement;

import static java.util.Objects.nonNull;
import static org.jboss.gwt.elemento.core.Elements.a;

public class ListItem<T> extends BaseListItem<HTMLAnchorElement, ListItem<T>> implements HasValue<ListItem<T>, T>
        , Selectable<ListItem<T>>, HasBackground<ListItem<T>>, Switchable<ListItem<T>>, TakesValue<T> {

    private T value;
    private ListGroup<T> parent;
    private boolean selected = false;
    private boolean disabled = false;
    private String style;
    private HTMLAnchorElement element= a().css(ListStyles.LIST_GROUP_ITEM).asElement();

    public ListItem(T value) {
        element.setAttribute("tabindex", "0");
        setElement(element);
        KeyboardEvents.listenOn(element).onEnter(evt -> {
            setSelectedItem();
        });
        this.value = value;
        addEventListener("click", e -> {
            setSelectedItem();
        });
        init(this);
    }

    public void setSelectedItem() {
        if (!disabled) {
            if (isSelected()) {
                deselect();
            } else {
                select();
            }
        }
    }

    public static <T> ListItem<T> create(T value) {
        return new ListItem<>(value);
    }

    @Override
    public ListItem<T> value(T value) {
        setValue(value);
        return this;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public ListItem<T> select() {
        return select(false);
    }

    @Override
    public ListItem<T> deselect() {
        return deselect(false);
    }

    @Override
    public ListItem<T> select(boolean silent) {
        if (parent.isSelectable()) {
            if (!parent.isMultiSelect())
                parent.getItems().forEach(tListItem -> tListItem.deselect(true));
            if (!selected) {
                style().add("active");
                this.selected = true;
                if (!silent)
                    parent.onSelectionChange(this);
            }
        }

        return this;
    }

    @Override
    public ListItem<T> deselect(boolean silent) {
        if (selected) {
            style().remove("active");
            this.selected = false;
            if (!silent) {
                parent.onSelectionChange(this);
            }
        }

        return this;
    }

    @Override
    public ListItem<T> disable() {
        if (!disabled) {
            deselect();
            style().add("disabled");
            this.disabled = true;
        }

        return this;
    }

    @Override
    public ListItem<T> enable() {
        if (disabled) {
            style().remove("disabled");
            this.disabled = false;
        }

        return this;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    public ListItem<T> setStyle(ListGroupStyle itemStyle) {
        return setStyle(itemStyle.getStyle());
    }

    private ListItem<T> setStyle(String itemStyle) {
        if (nonNull(this.style)) {
            style().remove(this.style);
        }
        style().add(itemStyle);
        this.style = itemStyle;
        return this;
    }

    public ListItem<T> success() {
        setStyle(ListGroupStyle.SUCCESS);
        return this;
    }

    public ListItem<T> warning() {
        setStyle(ListGroupStyle.WARNING);
        return this;
    }

    public ListItem<T> info() {
        setStyle(ListGroupStyle.INFO);
        return this;
    }

    public ListItem<T> error() {
        setStyle(ListGroupStyle.ERROR);
        return this;
    }

    @Override
    public ListItem<T> setBackground(Color background) {
        setStyle(background.getBackground());
        return this;
    }

    public ListItem<T> setHeading(String heading) {
        setHeaderText(heading);
        return this;
    }

    public ListItem<T> setText(String content) {
        setBodyText(content);
        return this;
    }

    @Deprecated
    public ListItem<T> appendContent(Node node) {
        this.appendChild(node);
        return this;
    }

    @Deprecated
    public ListItem<T> appendContent(IsElement isElement) {
        this.appendChild(isElement);
        return this;
    }

    public ListItem<T> appendChild(Node node) {
        asElement().appendChild(node);
        return this;
    }

    public ListItem<T> appendChild(IsElement isElement) {
        return appendChild(isElement.asElement());
    }

    void setParent(ListGroup<T> parent) {
        this.parent = parent;
    }
}
