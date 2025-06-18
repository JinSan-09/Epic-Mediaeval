package com.sanjin.screen;

import com.sanjin.EpicMediaeval;
import com.sanjin.enums.PeasantGuiTabType;
import com.sanjin.gui.AbstractTabContent;
import com.sanjin.gui.tab.BasicInfoTabContent;
import com.sanjin.gui.tab.EquipmentConfigTabContent;
import com.sanjin.gui.tab.TradeHistoryTabContent;
import com.sanjin.gui.widget.PeasantTabWidget;
import com.sanjin.gui.widget.RelationshipDisplayWidget;
import com.sanjin.menu.PeasantMenu;
import com.sanjin.network.packet.SwitchPeasantTabPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeasantScreen extends AbstractContainerScreen<PeasantMenu> {

    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(EpicMediaeval.MODID, "textures/gui/entity/peasant_gui.png");

    private static final int GUI_WIDTH = 232;
    private static final int GUI_HEIGHT = 241;
    private static final int RELATIONSHIP_X = 15;
    private static final int RELATIONSHIP_Y = 17;
    private static final int INFO_X = 15;
    private static final int INFO_Y = 45;

    private final List<PeasantTabWidget> tabWidgets = new ArrayList<>();
    private final Map<PeasantGuiTabType, AbstractTabContent> tabContents = new HashMap<>();
    private RelationshipDisplayWidget relationshipDisplay;
    private AbstractTabContent currentTabContent;

    public PeasantScreen(PeasantMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = GUI_WIDTH;
        this.imageHeight = GUI_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();

        this.relationshipDisplay = new RelationshipDisplayWidget(leftPos + RELATIONSHIP_X, topPos + RELATIONSHIP_Y, 60, 30, menu);
        this.addWidget(relationshipDisplay);

        initTabContents();

        initTabs();

        updateCurrentTab();
    }

    private void initTabContents() {
        tabContents.put(PeasantGuiTabType.BASIC_INFO,
                new BasicInfoTabContent(menu, leftPos + INFO_X, topPos + INFO_Y, imageWidth - 16, imageHeight - 50));
        tabContents.put(PeasantGuiTabType.TRADE_HISTORY,
                new TradeHistoryTabContent(menu, leftPos + 8, topPos + 45, imageWidth - 16, imageHeight - 50));
        tabContents.put(PeasantGuiTabType.SECRETS,
                new EquipmentConfigTabContent(menu, leftPos + 8, topPos + 45, imageWidth - 16, imageHeight - 50));
    }

    private void initTabs() {
        tabWidgets.clear();

        int tabX = leftPos + 8;
        int tabY = topPos - 24;
        int tabIndex = 0;

        for (PeasantGuiTabType tabType : PeasantGuiTabType.values()) {
            if (menu.canAccessTab(tabType)) {
                PeasantTabWidget tabWidget = new PeasantTabWidget(
                        tabX + (tabIndex * 28), tabY,
                        28, 24,
                        tabType,
                        this::onTabClicked,
                        tabType == menu.getCurrentTab()
                );

                tabWidgets.add(tabWidget);
                this.addRenderableWidget(tabWidget);
                tabIndex++;
            }
        }
    }

    private void onTabClicked(PeasantGuiTabType tabType) {
        if (menu.canAccessTab(tabType) && tabType != menu.getCurrentTab()) {
            PacketDistributor.sendToServer(new SwitchPeasantTabPacket(
                    menu.getPeasant().getId(), tabType.getSerializedName()));

            menu.setCurrentTab(tabType);
            updateCurrentTab();
            updateTabStates();
        }
    }

    private void updateCurrentTab() {
        this.currentTabContent = tabContents.get(menu.getCurrentTab());
        if (this.currentTabContent != null) {
            this.currentTabContent.init();
        }
    }

    private void updateTabStates() {
        for (PeasantTabWidget tab : tabWidgets) {
            tab.setActive(tab.getTabType() == menu.getCurrentTab());
        }
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(
                RenderType::guiTextured,
                BACKGROUND_TEXTURE,
                leftPos, topPos,
                0, 0,
                imageWidth, imageHeight,
                512, 512);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {

        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);

        relationshipDisplay.render(graphics, mouseX, mouseY, partialTick);
        if (currentTabContent != null) {
            currentTabContent.render(graphics, mouseX, mouseY, partialTick);
        }

        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(this.font, this.title, 8, 6, 0x404040, false);
    }

    @Override
    protected void renderTooltip(@NotNull GuiGraphics graphics, int x, int y) {
        super.renderTooltip(graphics, x, y);

        for (PeasantTabWidget tab : tabWidgets) {
            if (tab.isHoveredOrFocused() && tab.isHovered()) {
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(tab.getTabType().getDisplayName());

                if (!tab.isActive()) {
                    tooltip.add(Component.literal("点击切换到此选项卡"));
                }

                graphics.renderTooltip(this.font, tooltip, ItemStack.EMPTY.getTooltipImage(), x, y);
                break;
            }
        }

        if (relationshipDisplay.isMouseOver(x, y)) {
            relationshipDisplay.renderTooltip(graphics, x, y);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // 处理当前选项卡内容的鼠标点击
        if (currentTabContent != null && currentTabContent.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        // 检查是否点击了选项卡区域
        for (PeasantTabWidget tab : tabWidgets) {
            if (tab.isMouseOver(mouseX, mouseY)) {
                return false;
            }
        }

        return super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, mouseButton);
    }

    public @NotNull PeasantMenu getMenu() {
        return this.menu;
    }
}
