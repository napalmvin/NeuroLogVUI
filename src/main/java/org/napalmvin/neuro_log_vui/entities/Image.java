/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.napalmvin.neuro_log_vui.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author LOL
 */
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String path;
    @Lob
    private byte[] content;

    public Image(String path, byte[] content) {
        this.path = path;
        this.content = content;
    }

    public Image() {
    }

    public String getPath() {
        return path;
    }

    public byte[] getContent() {
        return content;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
