# -*- coding: utf-8 -*-
# Generated by Django 1.9.4 on 2016-04-22 06:57
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('NeuroLogApp', '0007_auto_20160421_1708'),
    ]

    operations = [
        migrations.AlterField(
            model_name='patients',
            name='photo',
            field=models.FileField(null=True, upload_to=''),
        ),
    ]
