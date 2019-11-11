package frame.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.border.EmptyBorder;

import frame.content.AbsTable;

public class Table extends ContentPane {

	public Table() {
		AbsTable table = new AbsTable() {

			@Override
			public Vector<Vector<Object>> getData() {
				return d.getDoubleVector(d.getRs("select * from TBL_Bus"));
			}

			@Override
			public Vector<Object> getColumn() {
				return toVector("차량번호", "출발지", "도착지", "첫차시간", "소요시간", "운임횟수", "운임금액");
			}
		};
		contentPane.add(table, BorderLayout.CENTER);
		table.setBorder(new EmptyBorder(40, 0, 0, 0));
		table.getScrp().setPreferredSize(new Dimension(600, 500));
		table.reload();
		set();
	}

}
