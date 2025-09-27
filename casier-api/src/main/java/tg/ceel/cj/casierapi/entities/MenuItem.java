/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "core_menu_items")
public class MenuItem extends BaseEntity {
    
    @Id
    @Column(name = "code", nullable = false)
    private String code;
    
    @Column(name = "label", nullable = false)
    private String label;
    
    @Column(name = "item_path", nullable = false)
    private String itemPath;
    
    @Column(name = "icon_class", nullable = true)
    private String iconClass;
    
    @Column(name = "weight", nullable = false)
    private int weight;
    
    @ManyToOne
    @JoinColumn(name = "parent_item_code", nullable = true)
    private MenuItem parentItem;
    
    @ManyToOne
    @JoinColumn(name = "parent_menu_code", nullable = false)
    private Menu parentMenu;
    
    @ManyToOne
    @JoinColumn(name = "permission_code", nullable = true)
    private Permission permission;
    
    @OneToMany( mappedBy = "parentItem")
    private List<MenuItem> items = new ArrayList<>();

    public MenuItem() {
    }

    public MenuItem(String code, String label, int weight) {
        this.code = code;
        this.label = label;
        this.weight = weight;
        this.itemPath = "#";
    }

    public MenuItem(String code, String label, String itemPath, String iconClass, int weight) {
        this.code = code;
        this.label = label;
        this.itemPath = itemPath;
        this.iconClass = iconClass;
        this.weight = weight;
    }

    public MenuItem(String code, String label, int weight, MenuItem parentItem, Menu parentMenu) {
        this.code = code;
        this.label = label;
        this.itemPath = "#";
        this.weight = weight;
        this.parentItem = parentItem;
        this.parentMenu = parentMenu;
    }

    public MenuItem(String code, String label, String itemPath, String iconClass, int weight, MenuItem parentItem, Menu parentMenu, Permission permission) {
        this.code = code;
        this.label = label;
        this.itemPath = itemPath;
        this.iconClass = iconClass;
        this.weight = weight;
        this.parentItem = parentItem;
        this.parentMenu = parentMenu;
        this.permission = permission;
    }
    
    public boolean isTopMenu() {
        return this.parentItem == null;
    }
    
    public boolean hasItems() {
        return this.items.size() >= 1;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getItemPath() {
        return itemPath;
    }

    public void setItemPath(String itemPath) {
        this.itemPath = itemPath;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public MenuItem getParentItem() {
        return parentItem;
    }

    public void setParentItem(MenuItem parentItem) {
        this.parentItem = parentItem;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MenuItem other = (MenuItem) obj;
        return Objects.equals(this.code, other.code);
    }

    @Override
    public String toString() {
        return "MenuItem{" + "code=" + code + ", label=" + label + ", itemPath=" + itemPath + ", weight=" + weight + '}';
    }
}
