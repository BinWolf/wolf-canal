package com.wolf.canal.util;

import com.alibaba.otter.canal.common.utils.JsonUtils;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.protobuf.format.JsonFormat;
import com.wolf.canal.bo.CanalBO;
import com.wolf.canal.bo.ColumnBO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 18/5/1 09:36.
 *
 * @author wolf
 */
public class CanalParseUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CanalParseUtil.class);

    public static final String ROWDATAS = "rowdatas";
    public static final String TABLE = "table";
    public static final String SCHEMA = "schema";

    private CanalParseUtil() {
    }

    public static CanalBO parseCanalBO(String message) {
        CanalBO bo = new CanalBO();
        if (StringUtils.isBlank(message)) {
            return bo;
        }

        Map<String, String> map = JsonUtils.unmarshalFromString(message, HashMap.class);
        bo.setSchema(map.get(SCHEMA));
        bo.setTable(map.get(TABLE));

        CanalEntry.RowChange rowChange;
        try {
            com.google.protobuf.Message.Builder builder = CanalEntry.RowChange.newBuilder();
            JsonFormat.merge(map.get(ROWDATAS), builder);
            rowChange = (CanalEntry.RowChange) builder.build();
        } catch (Exception e) {
            LOGGER.error("parse RowChange Message error, data:{}", map.get(ROWDATAS), e);
            return bo;
        }

        CanalEntry.EventType eventType = rowChange.getEventType();
        bo.setEventType(eventType);
        if (bo.getEventType() == CanalEntry.EventType.DELETE) {
            List<Map<String, ColumnBO>> rowDataList = Lists.newArrayList();
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                rowDataList.add(handleBeforeColumn(rowData.getBeforeColumnsList()));
            }
            bo.setRowDatas(rowDataList);
        } else {
            List<Map<String, ColumnBO>> rowDataList = Lists.newArrayList();
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                rowDataList.add(handleColumn(eventType, rowData.getBeforeColumnsList(), rowData.getAfterColumnsList()));
            }
            bo.setRowDatas(rowDataList);
        }

        return bo;
    }

    public static Map<String, ColumnBO> handleColumn(CanalEntry.EventType eventType, List<CanalEntry.Column> beforeColumns, List<CanalEntry.Column> afterColumns) {
        Map<String, ColumnBO> columnInfoMap = Maps.newHashMap();
        int size = afterColumns.size();
        for (int i = 0; i < size; i++) {
            CanalEntry.Column afterColumn = afterColumns.get(i);
            ColumnBO columnBO = new ColumnBO();
            columnBO.setName(afterColumn.getName());
            columnBO.setAfterValue(afterColumn.getValue());
            columnBO.setDataType(afterColumn.getMysqlType());
            columnBO.setUpdated(afterColumn.getUpdated());
            // 只有在更新操作时才会有操作前的值
            if (eventType == CanalEntry.EventType.UPDATE) {
                CanalEntry.Column beforeColumn = beforeColumns.get(i);
                columnBO.setBeforeValue(beforeColumn.getValue());
            }
            columnInfoMap.put(afterColumn.getName(), columnBO);
        }
        return columnInfoMap;
    }

    public static Map<String, ColumnBO> handleBeforeColumn(List<CanalEntry.Column> beforeColumns) {
        Map<String, ColumnBO> columnBOMap = Maps.newHashMap();
        for (CanalEntry.Column beforeColumn : beforeColumns) {
            ColumnBO columnBO = new ColumnBO();
            columnBO.setName(beforeColumn.getName());
            columnBO.setBeforeValue(beforeColumn.getValue());
            columnBO.setDataType(beforeColumn.getMysqlType());
            columnBO.setUpdated(beforeColumn.getUpdated());
            columnBOMap.put(beforeColumn.getName(), columnBO);
        }
        return columnBOMap;
    }
}
