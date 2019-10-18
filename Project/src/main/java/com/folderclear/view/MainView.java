package com.folderclear.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import com.folderclear.bo.BO;
import com.folderclear.constant.GlobalColor;
import com.folderclear.constant.GlobalPath;
import com.folderclear.constant.GlobalSize;
import com.folderclear.exception.FCException;
import com.folderclear.util.StringUtils;
import com.folderclear.view.basic.BasicButton;
import com.folderclear.view.basic.BasicDialog;
import com.folderclear.view.basic.BasicFileOpenChooser;
import com.folderclear.view.basic.BasicFileSaveChooser;
import com.folderclear.view.confirm.ConfirmAndClosePanel;
import com.folderclear.view.datatable.DataTablePanel;
import com.folderclear.view.folderselector.FolderSelecterPanel;
import com.folderclear.view.loadplan.LoadPlanPanel;
import com.folderclear.view.menu.MenuPanel;
import com.folderclear.vo.ConfigVO;

public class MainView extends JFrame implements WindowStateListener, ComponentListener, WindowListener {
	public JPanel mainPanel = null;
	public JPanel bottomPanel = null;
	public JPanel topPanel = null;
	public JPanel centerPanel = null;
	public BO bo = new BO();
	public MenuPanel menuPanel = null;
	public DataTablePanel dTableView = null;
	public ConfirmAndClosePanel confirmAndCanclePanel = null;
	public LoadPlanPanel loadPlanPanel = null;
	public FolderSelecterPanel folderSelecterPanel = null;
	public String[] columnNames = { "目录列表" };
	public final String defaultTitle = "批量删除工具";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainView() throws FileNotFoundException, IOException {
		initUI();// 初始化UI
		readPlanDataAction(bo.readConfig().getDefaultPlan());// 载入默认方案
	}

	public void initUI() throws FileNotFoundException, IOException {
		setTitle(defaultTitle);
		setBounds(GlobalSize.SCREENWINDTH / 2 - GlobalSize.MAINWINDTH / 2,
				GlobalSize.SCREENHEIGHT / 2 - GlobalSize.MAINWINDTH / 2, GlobalSize.MAINWINDTH, GlobalSize.MAINHEIGHT); // 设置窗口的属性
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addComponentListener(this);
		addWindowStateListener(this);
		addWindowListener(this);
		mainPanel = new JPanel();// 主页面
		mainPanel.setBackground(GlobalColor.MAINGRAY);
		BorderLayout borderLayout = new BorderLayout(0, 0);
		mainPanel.setLayout(borderLayout);
		add(mainPanel);
		initTop();
		initCenter();
		initBottom();
		setVisible(true);
	}

	// 北布局
	public void initTop() {
		topPanel = new JPanel();
		GridLayout topPanelGL = new GridLayout(1, 1, 0, 0);
		topPanel.setLayout(topPanelGL);
		topPanel.setBackground(GlobalColor.MAINGRAY);
		menuPanel = new MenuPanel() {

			@Override
			public void loadAction() {
				loadBtnAction();
			}

			@Override
			public void saveAction() {
				saveBtnAction();

			}

			@Override
			public void saveAsAction() {
				saveASBtnAction();
			}

		};
		topPanel.add(menuPanel);
		mainPanel.add("North", topPanel);
	}

	// 中布局
	public void initCenter() throws FileNotFoundException, IOException {
		centerPanel = new JPanel();
		centerPanel.setBackground(GlobalColor.MAINGRAY);
		BoxLayout boxLayout = new BoxLayout(centerPanel, BoxLayout.Y_AXIS);
		centerPanel.setLayout(boxLayout);
		mainPanel.add("Center", centerPanel);
		dTableView = new DataTablePanel(null, columnNames) {
			@Override
			public void removeBtnAction(ActionEvent e) {
				removeAction(e);
			}
		};// 数据列表页面
		centerPanel.add(dTableView);

		folderSelecterPanel = new FolderSelecterPanel() {// 文件选择页面
			@Override
			public void addAction(ActionEvent e, String text) {
				addBtnAction(e, text);
			}
		};

		centerPanel.add(folderSelecterPanel);
	}

	// 南布局
	public void initBottom() {
		bottomPanel = new JPanel();
		GridLayout bottomPanelGL = new GridLayout(1, 1, 0, 0);
		bottomPanel.setLayout(bottomPanelGL);
		bottomPanel.setBackground(GlobalColor.MAINGRAY);
		mainPanel.add("South", bottomPanel);
		confirmAndCanclePanel = new ConfirmAndClosePanel() {// 确认页面
			@Override
			public void okBtnAction(ActionEvent e) {
				okAction(e);
			}

			@Override
			public void closeBtnAction(ActionEvent e) {
				closeAction(e);
			}
		};
		bottomPanel.add(confirmAndCanclePanel);
	}

	public Object[][] initData() throws FileNotFoundException, IOException {
		// 初始化文件目录创建，包括默认配置
		BO.currentPlanpath = bo.readConfig().getDefaultPlan();
		// 获取默认数据
		return bo.readData(BO.currentPlanpath);
	}

	// 确认删除按钮
	public void okAction(ActionEvent e) {
		createSaveDialog(new AfterActionCallBack() {

			@Override
			public void yesAction() {
				try {
					StringBuffer stringBuffer = new StringBuffer("删除成功!\n");
					JTable table = dTableView.getBasicTable();
					if (table.getRowCount() <= 0) {
						throw new RuntimeException("列表内无数据");
					}
					for (int i = 0; i < table.getRowCount(); i++) {
						String folderPath = (String) table.getValueAt(i, 0);
						try {
							bo.delFolder(folderPath);
						} catch (FCException e2) {
							stringBuffer.append(e2.getMessage() + "\n");
						}
					}
					createMessageDialog(stringBuffer.toString(), JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e2) {
					e2.printStackTrace();
					createMessageDialog(e2.getMessage(), JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void noAction() {

			}

			@Override
			public void cancleAction() {

			}
		}, "是否确定要删除列表内文件数据");

	}

	// 关闭按钮
	public void closeAction(ActionEvent e) {
		if (BO.isModify) {
			createSaveDialog(new AfterActionCallBack() {
				@Override
				public void yesAction() {
					saveBtnAction();
					saveDefaultPlan();
					System.exit(0);// 退出
				}

				@Override
				public void noAction() {
					saveDefaultPlan();
					System.exit(0);// 退出
				}

				@Override
				public void cancleAction() {

				}
			}, "有数据修改,是否保存当前方案？");
		} else {
			saveDefaultPlan();
			System.exit(0);// 退出
		}

	}

	// 读取方案，更新主界面UI活动
	private void readPlanDataAction(String filepath) {
		if (StringUtils.isEmpty(filepath)) {
			return;
		}
		try {
			Object[][] data = null;
			data = bo.readData(filepath);
			dTableView.initTableUI(data);
			if (!filepath.equals(BO.currentPlanpath)) {
				updateUITitle(filepath);
			}
			BO.currentPlanpath = filepath;
		} catch (Exception e) {
			e.printStackTrace();
			createMessageDialog(e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}

	// 保存方案，更新主界面UI活动
	private void storePlanDataAction(String path) {
		int size = dTableView.getBasicTable().getRowCount();
		Object[][] data = new Object[size][1];
		for (int i = 0; i < data.length; i++) {
			data[i][0] = dTableView.getBasicTable().getValueAt(i, 0);
		}
		try {
			bo.createPlan(path, data);
			if (!path.equals(BO.currentPlanpath)) {
				updateUITitle(path);
			}
			BO.currentPlanpath = path;
			BO.isModify = false;
		} catch (Exception e) {
			e.printStackTrace();
			createMessageDialog(e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}

	// 载入方案选择器活动
	private void planChooserAction() {
		new BasicFileOpenChooser(GlobalPath.PLANS, "properties", BasicFileOpenChooser.FILES_ONLY) {
			@Override
			public void handleOpen(String filepath) {
				readPlanDataAction(filepath);
			}
		};
	}

	// 载入按钮活动
	private void loadBtnAction() {
		if (!BO.isModify) {
			planChooserAction();
		} else {
			createSaveDialog(new AfterActionCallBack() {
				@Override
				public void yesAction() {
					saveBtnAction();
					planChooserAction();
				}

				@Override
				public void noAction() {
					planChooserAction();
				}

				@Override
				public void cancleAction() {
				}
			}, "有数据修改,是否保存当前方案？");
		}
	}

	// 保存数据
	private void saveBtnAction() {
		if (StringUtils.isEmpty(BO.currentPlanpath)) {
			saveASBtnAction();
		} else {
			storePlanDataAction(BO.currentPlanpath);
		}
	}

	// 另存为
	private void saveASBtnAction() {
		new BasicFileSaveChooser(GlobalPath.PLANS, "properties", BasicFileOpenChooser.FILES_ONLY) {
			@Override
			public void handleSave(String filepath) {
				storePlanDataAction(filepath);
			}
		};
	}

	// 添加目录按钮事件
	public void addBtnAction(ActionEvent e, String text) {
		try {
			if (StringUtils.isEmpty(text)) {
				throw new RuntimeException("请选择要删除的目录");
			}
			Object[] data = new Object[] { text };
			dTableView.addData(data);
			BO.isModify = true;
		} catch (Exception e2) {
			e2.printStackTrace();
			createMessageDialog(e2.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}

	// 移除表条目按钮事件
	public void removeAction(ActionEvent e) {
		int[] rowindexs = dTableView.getBasicTable().getSelectedRows();
		ArrayList<String> column0Rows = new ArrayList<String>();
		for (int i = 0; i < rowindexs.length; i++) {
			column0Rows.add((String) dTableView.getBasicTable().getValueAt(rowindexs[i], 0));
		}

		try {
			// bo.removeData(BO.currentPlanpath, column0Rows);
			dTableView.removeData(rowindexs);
			BO.isModify = true;
		} catch (Exception e1) {
			e1.printStackTrace();
			createMessageDialog(e1.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
	}

	// 更新Title
	public void updateUITitle(String title) {
		setTitle(defaultTitle + "-" + title);
	}

	// 保存弹窗
	private void createSaveDialog(final AfterActionCallBack callBack, String message) {
		final BasicButton yesbtn = new BasicButton("是");
		final BasicButton nobtn = new BasicButton("否");
		final BasicButton canclebtn = new BasicButton("取消");
		BasicDialog closeDialog = new BasicDialog(null, "消息", message, yesbtn, nobtn, canclebtn) {
			@Override
			public void buttonAction(ActionEvent e) {
				this.dispose();
				if (e.getSource().equals(yesbtn)) {
					callBack.yesAction();
				} else if (e.getSource().equals(nobtn)) {
					callBack.noAction();
				} else if (e.getSource().equals(canclebtn)) {
					callBack.cancleAction();
				}
			}
		};
	}

	// 消息弹窗弹窗
	public void createMessageDialog(String message, int messageType) {
		JOptionPane.showMessageDialog(this, message, null, messageType);
	}

	// 执行回调
	public interface AfterActionCallBack {
		public void yesAction();

		public void noAction();

		public void cancleAction();
	}

	private void saveDefaultPlan() {
		if (!StringUtils.isEmpty(BO.currentPlanpath) && new File(BO.currentPlanpath).exists()) {
			ConfigVO vo = new ConfigVO();
			vo.setDefaultPlan(BO.currentPlanpath);
			try {
				bo.writeConfig(vo);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void windowStateChanged(WindowEvent e) {
		if (e.getOldState() != e.getNewState()) {
			switch (e.getNewState()) {
			case Frame.MAXIMIZED_VERT:
				// 最大化
			case Frame.ICONIFIED:
				// 最小化
			case Frame.NORMAL:
				// 恢复
			default:
				int marginWidth = getWidth() - GlobalSize.MAINWINDTH;
				int marginHeight = getHeight() - GlobalSize.MAINHEIGHT;
				dTableView.fitSize(new Dimension(marginWidth, marginHeight));
				folderSelecterPanel.fitSize(new Dimension(marginWidth, marginHeight));
				menuPanel.fitSize(new Dimension(marginWidth, marginHeight));
				confirmAndCanclePanel.fitSize(new Dimension(marginWidth, marginHeight));
				break;
			}
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		int marginWidth = getWidth() - GlobalSize.MAINWINDTH;
		int marginHeight = getHeight() - GlobalSize.MAINHEIGHT;
		dTableView.fitSize(new Dimension(marginWidth, marginHeight));
		folderSelecterPanel.fitSize(new Dimension(marginWidth, marginHeight));
		menuPanel.fitSize(new Dimension(marginWidth, marginHeight));
		confirmAndCanclePanel.fitSize(new Dimension(marginWidth, marginHeight));
	}

	@Override
	public void componentMoved(ComponentEvent e) {

	}

	@Override
	public void componentShown(ComponentEvent e) {

	}

	@Override
	public void componentHidden(ComponentEvent e) {

	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		new MainView();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		closeAction(null);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
