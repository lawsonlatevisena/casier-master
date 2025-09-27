/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tg.ceel.cj.casierapi.utils;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author believe
 */
@XmlEnum(String.class)
public enum ResponseCode {
    @XmlEnumValue("SUCCESS")
    SUCCESS,
    @XmlEnumValue("UNKNOW_ERROR")
    UNKNOW_ERROR,
    @XmlEnumValue("SYNC_SUCCESS")
    SYNC_SUCCESS,
    @XmlEnumValue("SYNC_ERROR")
    SYNC_ERROR,
    @XmlEnumValue("LOAD_ERROR")
    LOAD_ERROR,
    @XmlEnumValue("LOAD_SUCCESS")
    LOAD_SUCCESS,
    @XmlEnumValue("LOGIN_ERROR")
    LOGIN_ERROR,
    @XmlEnumValue("NOT_FOUND")
    NOT_FOUND,
    @XmlEnumValue("FAILLED")
    FAILLED,
    @XmlEnumValue("PROHIBITED")
    PROHIBITED,
    @XmlEnumValue("INTERNAL_SERVER_ERROR")
    INTERNAL_SERVER_ERROR
}