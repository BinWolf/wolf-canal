package com.wolf.canal.bo;

import com.alibaba.otter.canal.protocol.CanalEntry;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;

/**
 * Created on 18/5/1 09:26.
 *
 * @author wolf
 */
public class CanalBO {
    private String schema;
    private String table;
    private CanalEntry.EventType eventType;
    private List<Map<String, ColumnBO>> rowDatas;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public CanalEntry.EventType getEventType() {
        return eventType;
    }

    public void setEventType(CanalEntry.EventType eventType) {
        this.eventType = eventType;
    }

    public List<Map<String, ColumnBO>> getRowDatas() {
        return rowDatas;
    }

    public void setRowDatas(List<Map<String, ColumnBO>> rowDatas) {
        this.rowDatas = rowDatas;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
