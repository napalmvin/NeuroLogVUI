# -*- coding: utf-8 -*-
# Generated by Django 1.9.4 on 2016-04-14 11:50
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('NeuroLogApp', '0004_auto_20160414_1445'),
    ]

    operations = [
        migrations.AlterField(
            model_name='doctors',
            name='birth_date',
            field=models.DateField(),
        ),
    ]