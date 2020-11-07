package com.arulvakku.lyrics.app.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.arulvakku.lyrics.app.R
import kotlinx.android.synthetic.main.activity_lyrics_screen.*


class LyricsScreenActivity : BaseActivity() {

    private val title by lazy {
        intent.getStringExtra("title")
    }

    private val song by lazy {
        intent.getStringExtra("song")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyrics_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        setData()
    }

    /**
     * set title and song data
     */
    private fun setData() {
        supportActionBar?.title = title
        lyricsTExt.text = song
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.lyrics_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return when (id) {
            R.id.share -> {
                shareSong(song);
                true
            }
            R.id.copy -> {
                copyTextToClipboard(song)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Sharing songs
     */

    private fun shareSong(song: String) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, song);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
    }

    /**
     * Copy songs to clipboard
     */
    private fun copyTextToClipboard(song: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", song)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }


}